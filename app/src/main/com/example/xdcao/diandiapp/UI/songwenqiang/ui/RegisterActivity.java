package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xdcao.diandiapp.BackUp.caohao.activity.LoginActivity;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
                userRegist();
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


    private void userRegist() {
        if (mEtMobileNumber.getText().toString().length()!=11){
            Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
        }else if(!mEtRePassWord.getText().toString().equals(mEtPassWord.getText().toString())){
            Toast.makeText(RegisterActivity.this,"确认密码错误",Toast.LENGTH_SHORT).show();
        }else {
            MyUser user=new MyUser();
            user.setUsername(mEtMobileNumber.getText().toString());
            user.setPassword(mEtPassWord.getText().toString());
            user.setNickName(mEtNickName.getText().toString());
            user.setSignName(mEtSignName.getText().toString());
            user.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if(e==null){
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,SignUpActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(RegisterActivity.this, "注册失败，检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
