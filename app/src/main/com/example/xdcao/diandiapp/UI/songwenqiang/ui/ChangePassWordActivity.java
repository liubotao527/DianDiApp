package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xdcao.diandiapp.BackUp.caohao.actions.UserAction;
import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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

                String old=mEtOldPassWord.getText().toString();
                String confirmOld=mEtReOldPassWord.getText().toString();
                String newPass=mEtNewPassWord.getText().toString();
                String confirmNew=mEtReNewPassWord.getText().toString();
                if (!old.equals(confirmOld)){
                    Toast.makeText(ChangePassWordActivity.this,"请确认旧密码输入一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!newPass.equals(confirmNew)){
                    Toast.makeText(ChangePassWordActivity.this,"请确认新密码输入一致",Toast.LENGTH_SHORT).show();
                }else {
                    updateCurrentUserPwd(old,newPass);
                }


            }
        });
    }

    private void updateCurrentUserPwd(String oldPwd,String newPwd){
        BmobUser.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ChangePassWordActivity.this,"密码修改成功，可以用新密码进行登录",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChangePassWordActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
