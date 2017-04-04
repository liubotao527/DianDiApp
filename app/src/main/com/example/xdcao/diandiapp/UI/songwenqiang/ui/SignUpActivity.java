package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.activity.LoginActivity;
import com.example.xdcao.diandiapp.BackUp.caohao.activity.PostActivity;
import com.example.xdcao.diandiapp.BackUp.caohao.activity.RegisterActivity;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.prefs.UserPrefs;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    public UserPrefs userPrefs;
    public TextView skipTextView;
    private EditText telephone;
    private EditText password;
    private TextView login;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        BmobInit();
        skipTextView = (TextView) findViewById(R.id.skip);

        telephone=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        login=(TextView) findViewById(R.id.login);
        signUp=(TextView)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/4/4
                userLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/4/4
                Intent intent=new Intent(SignUpActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        userPrefs = new UserPrefs(SignUpActivity.this);
        if(!TextUtils.isEmpty(userPrefs.getAuthToken())){
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
            return;
        }
        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            }
        });
    }

    private void userLogin() {
        MyUser user=new MyUser();
        user.setUsername(telephone.getText().toString());
        user.setPassword(password.getText().toString());
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Log.i(TAG, "done: "+"登陆成功");
                    Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Log.i(TAG, "done: "+"登录失败");
                }
            }
        });
    }


    private void BmobInit() {
//        Bmob.initialize(this,"2e17b59fa1eb4f65250b5e02abc93c31");
        BmobConfig config=new BmobConfig.Builder(this).setApplicationId("2e17b59fa1eb4f65250b5e02abc93c31")
                .build();
        Bmob.initialize(config);

    }


}
