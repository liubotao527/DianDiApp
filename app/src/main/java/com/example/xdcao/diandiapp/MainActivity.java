package com.example.xdcao.diandiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by xdcao on 2017/1/14.
 */

public class MainActivity extends Activity {

    private Button toSubmitAc;
    private Button toScanAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initActivity();
        BmobInit();

    }

    private void initActivity(){
        toSubmitAc=(Button)findViewById(R.id.toSubmitAc);
        toScanAc=(Button)findViewById(R.id.toScanAc);
        toSubmitAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SubmitActivity.class);
                startActivity(intent);
            }
        });
        toScanAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ScanActivity.class);
                startActivity(intent);
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

