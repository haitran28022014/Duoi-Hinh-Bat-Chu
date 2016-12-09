package com.example.haitran.ailatrieuphu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haitran.ailatrieuphu.R;
import com.example.haitran.ailatrieuphu.adapter.MoneyAdapter;
import com.example.haitran.ailatrieuphu.dialog.HelpCallDialog;
import com.example.haitran.ailatrieuphu.dialog.HelpDialog;
import com.example.haitran.ailatrieuphu.mamager.DatabaseManager;
import com.example.haitran.ailatrieuphu.model.Question;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 10/24/2016.
 */

public class HomeActivity extends Activity implements View.OnClickListener, Runnable {
    public static final String TAG = "HomeActivity";
    private static final int UPDATE_TEXT_VIEW = 0;
    private int time = 30;
    public static int positionNavigation = 14;
    private int[] buttonResult = {R.id.linear_select_a, R.id.linear_select_b, R.id.linear_select_c, R.id.linear_select_d};
    private ArrayList<LinearLayout> linearResult = new ArrayList<>();
    private Animation animation;
    private DatabaseManager databaseManager;
    private MoneyAdapter moneyAdapter;
    private ArrayList<Question> arrQuestions;
    private String[] arrMoney;
    private int result;
    private int checkTrueOrFalse;
    private int indexQuestion = 0;
    private boolean checkPressed = false;
    private boolean isRunning = false;
    private Handler handler;


    @Bind(R.id.linear_select_a)
    LinearLayout linearSelectA;

    @Bind(R.id.txt_question_number)
    TextView txtQuestionNumber;

    @Bind(R.id.txt_question)
    TextView txtQuestion;

    @Bind(R.id.txt_case_a)
    TextView txtCaseA;

    @Bind(R.id.txt_case_b)
    TextView txtCaseB;

    @Bind(R.id.txt_case_c)
    TextView txtCaseC;

    @Bind(R.id.txt_case_d)
    TextView txtCaseD;

    @Bind(R.id.btn_stop)
    Button btnStop;

    @Bind(R.id.btn_50)
    Button btn50;

    @Bind(R.id.btn_audience)
    Button btnAudience;

    @Bind(R.id.btn_call)
    Button btnCall;

    @Bind(R.id.btn_change_question)
    Button btnChange;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.nv_view)
    NavigationView navigationView;

    @Bind(R.id.list_view)
    ListView listView;

    @Bind(R.id.txt_time)
    TextView txtTime;

    @Bind(R.id.txt_money)
    TextView txtMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViews();
        loadQuestion(indexQuestion);
        initListener();
        initNavigationMoney();
    }

    private void initViews() {
        arrQuestions = new ArrayList<>();
        databaseManager = new DatabaseManager(this);
        arrQuestions.addAll(databaseManager.get15Question());

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = metrics.widthPixels;
        navigationView.setLayoutParams(params);

        drawerLayout.openDrawer(GravityCompat.START);
        isRunning = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawers();
                updateTime();
            }
        }, 5000);
        animation = AnimationUtils.loadAnimation(this, R.anim.custom_button_result_selected);
        for (int i = 0; i < buttonResult.length; i++) {
            linearResult.add((LinearLayout) findViewById(buttonResult[i]));
        }
    }

    private void loadQuestion(int index) {
        txtQuestionNumber.setText("Câu " + (index + 1));
        txtQuestion.setText(arrQuestions.get(index).getQuestion());
        txtCaseA.setText(arrQuestions.get(index).getCaseA());
        txtCaseB.setText(arrQuestions.get(index).getCaseB());
        txtCaseC.setText(arrQuestions.get(index).getCaseC());
        txtCaseD.setText(arrQuestions.get(index).getCaseD());
        result = arrQuestions.get(index).getTrueCase();
    }

    private void initListener() {
        for (int i = 0; i < linearResult.size(); i++) {
            linearResult.get(i).setOnClickListener(this);
        }
        btnAudience.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnChange.setTag("change");
        btn50.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStop.setTag("stop");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_audience:
                btnAudience.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_audience_x);
                isRunning = false;
                btnAudience.setEnabled(false);
                break;
            case R.id.btn_50:
                btn50.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_5050_x);
                ArrayList<Integer> arrNumber = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    if (i != result - 1) {
                        arrNumber.add(i);
                    }
                }
                Collections.shuffle(arrNumber);
                for (int i = 0; i < arrNumber.size(); i++) {
                    if (i != 0) {
                        linearResult.get(i).setVisibility(View.INVISIBLE);
                    }
                }
                isRunning = false;
                btn50.setEnabled(false);
                break;
            case R.id.btn_call:
                btnCall.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call_x);
                HelpCallDialog helpCallDialog=new HelpCallDialog(this);
                helpCallDialog.show();
                isRunning = false;
                btnCall.setEnabled(false);
                break;
            case R.id.btn_stop:
                HelpDialog helpDialog = new HelpDialog(this);
                helpDialog.getTxtQuestionDialog().setText("Bạn thực sự muốn dừng cuộc chơi ?");
                showViewDialog(helpDialog, view);
                break;
            case R.id.btn_change_question:
                btnChange.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_change_question_x);
                isRunning = false;
                helpDialog = new HelpDialog(this);
                helpDialog.getTxtQuestionDialog().setText("Bạn thực sự muốn đổi câu hỏi ?");
                showViewDialog(helpDialog, view);
                btnChange.setEnabled(false);
                break;
            default:
                handlerSelectResult(view);
                break;
        }

    }

    public void showViewDialog(final HelpDialog helpDialog, final View view) {
        WindowManager.LayoutParams windowManager = new WindowManager.LayoutParams();
        Window window = helpDialog.getWindow();
        windowManager.copyFrom(window.getAttributes());
        windowManager.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(windowManager);
        helpDialog.setOnClickViewDialog(new HelpDialog.OnClickViewDialog() {
            @Override
            public void onClickYes() {
                helpDialog.dismiss();
                handlerDelayTimeHelp();
                if (view.getTag().equals("change")) {
                    Question question = databaseManager.getOneQuestion("Question" + (indexQuestion + 1));
                    arrQuestions.remove(indexQuestion);
                    arrQuestions.add(indexQuestion, question);
                    loadQuestion(indexQuestion);
                }
            }
        });
        helpDialog.show();
    }

    public void handlerDelayTimeHelp() {
        isRunning = true;
        time = Integer.parseInt(txtTime.getText().toString());
        updateTime();
    }

    private void initNavigationMoney() {
        arrMoney = getResources().getStringArray(R.array.array_money);
        moneyAdapter = new MoneyAdapter(arrMoney);
        listView.setAdapter(moneyAdapter);
    }

    private void handlerSelectResult(View view) {
        for (int i = 0; i < linearResult.size(); i++) {
            if (view.getId() == buttonResult[i]) {
                isRunning = false;
                if (checkPressed == false) {
                    checkPressed = true;
                    checkTrueOrFalse = i + 1;
                    linearResult.get(i).setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                    linearResult.get(i).startAnimation(animation);
                    final int finalI = i;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (checkTrueOrFalse == result) {
                                linearResult.get(finalI).setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                linearResult.get(finalI).startAnimation(animation);
                                indexQuestion = indexQuestion + 1;
                                positionNavigation = 14 - indexQuestion;
                                moneyAdapter.notifyDataSetChanged();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        checkPressed = false;
                                        linearResult.get(finalI).setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                        drawerLayout.openDrawer(GravityCompat.START);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                initViewQuestion();
                                            }
                                        }, 3000);
                                    }
                                }, 3000);
                            } else {
                                isRunning = false;
                                linearResult.get(finalI).setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                                linearResult.get(result - 1).setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                linearResult.get(result - 1).startAnimation(animation);
                            }
                        }
                    }, 3000);
                }
            }
        }
    }

    public void initViewQuestion(){
        isRunning = true;
        time = 30;
        updateTime();
        String[] data = arrMoney[positionNavigation + 1].split("\\t");
        txtMoney.setText(data[1]);
        for (int i = 0; i < 4; i++) {
            linearResult.get(i).setVisibility(View.VISIBLE);
        }
        drawerLayout.closeDrawers();
        loadQuestion(indexQuestion);
    }

    public void updateTime() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_TEXT_VIEW:
                        txtTime.setText(msg.arg1 + "");
                        break;
                    default:
                        break;
                }
            }
        };
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        while (time >= 0 && isRunning) {
            Message message = new Message();
            message.what = UPDATE_TEXT_VIEW;
            message.arg1 = time;
            handler.sendMessage(message);
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
