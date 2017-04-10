package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
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

            mEtMobileNumber.setError("请输入正确的手机号码");
        }else if(mEtPassWord.getText().toString().length()<6){
            mEtPassWord.setError("密码长度至少6位");
        }else if(!mEtRePassWord.getText().toString().equals(mEtPassWord.getText().toString())){
            mEtPassWord.setError("确认密码错误");
        }else {
            MyUser user=new MyUser();
            user.setUsername(mEtMobileNumber.getText().toString());
            user.setPassword(mEtPassWord.getText().toString());
            String nickname = mEtNickName.getText().toString();
            String signName = mEtSignName.getText().toString();

            //将此时的昵称和个性签名保存到SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            if(!TextUtils.isEmpty(nickname)){
                user.setNickName(nickname);
                String flag = "nickname";
                String data = nickname;
                editor.putString(flag,data);
                editor.apply();
            }
            if(!TextUtils.isEmpty(signName)){
                user.setSignName(signName);
                String flag = "signName";
                String data = signName;
                editor.putString(flag,data);
                editor.apply();
            }

            user.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if(e==null){
                        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("正在注册...");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        Intent intent=new Intent(RegisterActivity.this,SignUpActivity.class);
                                        startActivity(intent);
                                        // onSignupFailed();
                                        progressDialog.dismiss();
                                        finish();
                                    }
                                }, 1000);
                    }else {
                        Toast.makeText(RegisterActivity.this, "注册失败，检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
