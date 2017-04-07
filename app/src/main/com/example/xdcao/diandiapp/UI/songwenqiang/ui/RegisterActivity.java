package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;

/**
 * et_input_mobile
 * et_input_password
 * et_input_reEnterPassword
 * et_input_nickname
 * et_input_sign_name
 * btn_sign_up
 * tv_login
 */
public class RegisterActivity extends AppCompatActivity {


    private EditText mEtMobileNumber,mEtPassWord,mEtRePassWord,mEtNickName,mEtSignName;

    private TextView mTvLogin;

    private AppCompatButton mAcbSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEtMobileNumber = (EditText)findViewById(R.id.et_input_mobile);
        mEtPassWord = (EditText) findViewById(R.id.et_input_password);
        mEtRePassWord = (EditText) findViewById(R.id.et_input_reEnterPassword);
        mEtNickName = (EditText) findViewById(R.id.et_input_nickname);
        mEtSignName = (EditText) findViewById(R.id.et_input_sign_name);

        mTvLogin = (TextView) findViewById(R.id.tv_login);

        mAcbSignUp = (AppCompatButton) findViewById(R.id.btn_sign_up);
        mAcbSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo  编写注册的代码



//                finish();
            }
        });

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,SignUpActivity.class));
                finish();

            }
        });

    }
}
