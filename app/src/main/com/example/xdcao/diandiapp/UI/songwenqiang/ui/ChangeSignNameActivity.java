package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xdcao.diandiapp.BackUp.caohao.actions.UserAction;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.R;

public class ChangeSignNameActivity extends AppCompatActivity {

    private ImageView mIvBack,mIvSave;

    private EditText mEtSignName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sign_name);

        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvSave = (ImageView)findViewById(R.id.iv_save);
        mEtSignName = (EditText) findViewById(R.id.et_sign_name);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 返回设置界面
                backToMainActivity("extra_data","mIvBack");
            }
        });

        mIvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 保存 返回设置界面
                if(TextUtils.isEmpty(mEtSignName.getText().toString())){
                    mEtSignName.setError("请输入你的个性签名");
                    return;
                }
                MyUser user=MyUser.getCurrentUser(MyUser.class);
                user.setSignName(mEtSignName.getText().toString());
                UserAction.updateUser(user);
                String flag = "signName";
                String data = mEtSignName.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString(flag,data);
                editor.apply();
                backToMainActivity("extra_data","mIvBack");
            }
        });
    }

    public void backToMainActivity(String flag,String data){
        Intent intent = new Intent(ChangeSignNameActivity.this,MainActivity.class);
        intent.putExtra(flag,data);
        startActivity(intent);
        finish();
    }
}
