package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xdcao.diandiapp.R;

public class ChangePassWordActivity extends AppCompatActivity {

    private EditText mEtOldPassWord,mEtReOldPassWord,mEtNewPassWord,mEtReNewPassWord;

    private ImageView mIvBack;

    private AppCompatButton mAcbSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);

        mEtOldPassWord = (EditText) findViewById(R.id.et_old_password);
        mEtReOldPassWord = (EditText) findViewById(R.id.et_reenter_old_password);
        mEtNewPassWord = (EditText) findViewById(R.id.et_new_password);
        mEtReNewPassWord = (EditText) findViewById(R.id.et_reenter_new_password);

        mIvBack = (ImageView) findViewById(R.id.iv_back);

        mAcbSave = (AppCompatButton) findViewById(R.id.btn_save);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo  返回设置界面
            }
        });

        mAcbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 保存并返回设置界面
            }
        });
    }
}
