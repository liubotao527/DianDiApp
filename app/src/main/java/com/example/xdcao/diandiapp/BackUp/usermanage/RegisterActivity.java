package com.example.xdcao.diandiapp.BackUp.usermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xdcao on 2017/1/16.
 */

public class RegisterActivity extends Activity {

    private EditText userInput;
    private EditText pwdInput;
    private EditText sexInput;
    private EditText ageInput;
    private Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initActivity();
    }

    private void initActivity() {

        userInput=(EditText)findViewById(R.id.user_input);
        pwdInput=(EditText)findViewById(R.id.pwd_input);
        sexInput=(EditText)findViewById(R.id.sex_input);
        ageInput=(EditText)findViewById(R.id.age_input);
        regist=(Button)findViewById(R.id.regist);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegist();
            }
        });

    }

    private void userRegist() {
        MyUser user=new MyUser();
        user.setUsername(userInput.getText().toString());
        user.setPassword(pwdInput.getText().toString());
        user.setSex(sexInput.getText().toString().equals("男")?true:false);
        user.setAge(Integer.parseInt(ageInput.getText().toString()));
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterActivity.this, "注册失败，检查网络连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
