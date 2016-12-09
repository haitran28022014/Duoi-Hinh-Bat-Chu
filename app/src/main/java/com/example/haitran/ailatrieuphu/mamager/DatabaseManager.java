package com.example.haitran.ailatrieuphu.mamager;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.haitran.ailatrieuphu.model.Question;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseManager {
    private String DATABASE_NAME = "Question";
    private String DATABASE_PATH = Environment.getDataDirectory().getAbsolutePath() +
            "/data/com.example.haitran.ailatrieuphu/";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context) {
        this.context = context;
        copyDatabases();
    }

    private void copyDatabases() {
        AssetManager asset = context.getAssets();
        try {
            File file = new File(DATABASE_PATH + DATABASE_NAME);
            if (file.exists()) {
                return;
            }
            DataInputStream in = new DataInputStream(asset.open(DATABASE_NAME));
            DataOutputStream out = new DataOutputStream(new FileOutputStream(DATABASE_PATH + DATABASE_NAME));
            byte[] b = new byte[1024];
            int length;
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDatabases() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        }
    }

    private void closeDatabases() {
        if (sqLiteDatabase != null || sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public void insert(String tableName, ContentValues values) {
        openDatabases();
        sqLiteDatabase.insert(tableName, null, values);
        closeDatabases();
    }

    public void delete(String tableName, String whereClause, String[] whereArgs) {
        openDatabases();
        sqLiteDatabase.delete(tableName, whereClause, whereArgs);
        closeDatabases();
    }

    public void update(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        openDatabases();
        sqLiteDatabase.update(tableName, values, whereClause, whereArgs);
        closeDatabases();
    }


    public Question getOneQuestion(String tableName) {
        openDatabases();
        Question question = new Question();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " order by random() limit 1", null);
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            String q = cursor.getString(cursor.getColumnIndex("Question"));
            String a = cursor.getString(cursor.getColumnIndex("CaseA"));
            String b = cursor.getString(cursor.getColumnIndex("CaseB"));
            String c = cursor.getString(cursor.getColumnIndex("CaseC"));
            String d = cursor.getString(cursor.getColumnIndex("CaseD"));
            int t = cursor.getInt(cursor.getColumnIndex("TrueCase"));
            question = new Question(q, a, b, c, d, t);
        }
        cursor.close();
        closeDatabases();
        return question;
    }

    public ArrayList<Question> get15Question() {
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Question question = getOneQuestion("Question" + i);
            if (question!=null){
                questions.add(question);
            }
        }
        return questions;
    }
}
