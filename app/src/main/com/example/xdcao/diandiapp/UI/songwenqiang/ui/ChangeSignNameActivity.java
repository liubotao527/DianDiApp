package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
            }
        });

        mIvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 保存 返回设置界面
            }
        });
    }
}
