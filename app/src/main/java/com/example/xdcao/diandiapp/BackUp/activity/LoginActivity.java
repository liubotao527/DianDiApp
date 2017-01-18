package com.example.xdcao.diandiapp.BackUp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xdcao.diandiapp.BackUp.bean.MyUser;
import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xdcao on 2017/1/16.
 */

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private EditText nameInput;
    private EditText pwdInput;
    private Button loginBtn;
    private Button toRegistAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLogin();
        setContentView(R.layout.login_layout);
        initActivity();
    }

    private void autoLogin() {
        MyUser currentUser=BmobUser.getCurrentUser(MyUser.class);
        if(currentUser!=null){
            Intent intent=new Intent(LoginActivity.this,PostActivity.class);
            startActivity(intent);
        }else {
            return;
        }
    }

    private void initActivity() {
        nameInput=(EditText)findViewById(R.id.name_input);
        pwdInput=(EditText)findViewById(R.id.pwd_input);
        loginBtn=(Button)findViewById(R.id.login_btn);
        toRegistAc=(Button)findViewById(R.id.toRegistAc);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        toRegistAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {
        MyUser user=new MyUser();
        user.setUsername(nameInput.getText().toString());
        user.setPassword(pwdInput.getText().toString());
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Log.i(TAG, "done: "+"登陆成功");
                    Intent intent=new Intent(LoginActivity.this,PostActivity.class);
                    startActivity(intent);
                }else {
                    Log.i(TAG, "done: "+"登录失败");
                }
            }
        });
    }




}
