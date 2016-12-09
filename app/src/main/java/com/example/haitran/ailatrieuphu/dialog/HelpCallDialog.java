package com.example.haitran.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.haitran.ailatrieuphu.R;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/28/2016.
 */

public class HelpCallDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int[] arrButton = {R.id.btn_help_01, R.id.btn_help_02, R.id.btn_help_03, R.id.btn_help_04};
    private ArrayList<Button> arrayList;

    public HelpCallDialog(Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.custom_dialog_audience);
        initViews();
    }

    private void initViews() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < arrButton.length; i++) {
            arrayList.add((Button) findViewById(arrButton[i]));
        }
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
    }
}
