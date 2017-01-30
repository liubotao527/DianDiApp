package com.example.xdcao.diandiapp.BackUp.caohao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.Lost;
import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xdcao on 2017/1/14.
 */

public class SubmitActivity extends BaseActivity{

    private EditText title;
    private EditText describe;
    private EditText phone;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_layout);
        initialActivity();
    }

    private void initialActivity() {
        title=(EditText)findViewById(R.id.title);
        describe=(EditText)findViewById(R.id.describe);
        phone=(EditText)findViewById(R.id.phone);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lost lostInfo=new Lost();
                lostInfo.setTitle(title.getText().toString());
                lostInfo.setDescribe(describe.getText().toString());
                lostInfo.setPhone(phone.getText().toString());
                lostInfo.save(new SaveListener<String>() {
                    @Override
                    public void done(String o, BmobException e) {
                        if(e==null){
                            System.out.print("ok");
                        }else {
                            System.out.print(e.toString());
                        }
                    }
                });
            }
        });
    }
}
