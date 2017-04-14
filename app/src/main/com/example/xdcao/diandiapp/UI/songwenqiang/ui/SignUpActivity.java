package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.ImageFetcher;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.prefs.UserPrefs;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "bmob";

    public UserPrefs userPrefs;
    private EditText mEtAccount,mEtPassWord;
    private AppCompatButton mAcbLogin;
    private TextView mTvNewAccount,mTvSkip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
//        autoLogin();
    }

    private void initView() {

        mTvNewAccount = (TextView) findViewById(R.id.tv_new_account);



        mEtAccount = (EditText) findViewById(R.id.et_input_account);
        mEtPassWord = (EditText) findViewById(R.id.et_input_password);

        mAcbLogin = (AppCompatButton) findViewById(R.id.btn_login);



        mTvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,RegisterActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });



        mAcbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo  编写登录的代码
                userLogin();
            }
        });
    }


    private void autoLogin() {
        MyUser currentUser=BmobUser.getCurrentUser(MyUser.class);
        if(currentUser!=null){
            String nickname = currentUser.getNickName();
            String signName = currentUser.getSignName();
            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            if(!TextUtils.isEmpty(nickname)){
                currentUser.setNickName(nickname);
                String flag = "nickname";
                String data = nickname;
                editor.putString(flag,data);
                editor.apply();
            }
            if(!TextUtils.isEmpty(signName)){
                currentUser.setSignName(signName);
                String flag = "signName";
                String data = signName;
                editor.putString(flag,data);
                editor.apply();
            }
            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            return;
        }
    }


    private void userLogin() {
        final MyUser user=new MyUser();
        user.setUsername(mEtAccount.getText().toString());
        user.setPassword(mEtPassWord.getText().toString());
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser bmobUser, BmobException e) {
                if(e==null){
                    Log.i("bmob", "done: "+"登陆成功");
                    String nickname = bmobUser.getNickName();
                    String signName = bmobUser.getSignName();
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    if(!TextUtils.isEmpty(nickname)){
                        bmobUser.setNickName(nickname);
                        String flag = "nickname";
                        String data = nickname;
                        editor.putString(flag,data);
                        editor.apply();
                    }
                    if(!TextUtils.isEmpty(signName)){
                        bmobUser.setSignName(signName);
                        String flag = "signName";
                        String data = signName;
                        editor.putString(flag,data);
                        editor.apply();
                    }
                    Log.d(TAG, "done: "+bmobUser.getNickName());
                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("正在登录...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    // onSignupFailed();
                                    progressDialog.dismiss();
                                    finish();
                                }
                            }, 1000);


                }else {
                    Log.i("bmob", "done: "+"登录失败");
                    Log.d("bmob", "done: "+e);
                    Toast.makeText(SignUpActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





}
