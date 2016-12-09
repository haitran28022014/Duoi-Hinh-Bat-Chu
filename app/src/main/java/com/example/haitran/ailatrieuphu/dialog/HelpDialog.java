package com.example.haitran.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.haitran.ailatrieuphu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 10/27/2016.
 */

public class HelpDialog extends Dialog implements View.OnClickListener {
    private OnClickViewDialog onClickViewDialog;
    @Bind(R.id.txt_question_dialog)
    TextView txtQuestionDialog;

    @Bind(R.id.btn_yes)
    Button btnYes;

    @Bind(R.id.btn_no)
    Button btnNo;

    public HelpDialog(Context context) {
        super(context);
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                onClickViewDialog.onClickYes();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
    }

    public TextView getTxtQuestionDialog() {
        return txtQuestionDialog;
    }

    public void setOnClickViewDialog(OnClickViewDialog onClickViewDialog) {
        this.onClickViewDialog = onClickViewDialog;
    }

    public interface OnClickViewDialog{
        void onClickYes();
    }
}
