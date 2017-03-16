package com.example.xdcao.diandiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017-3-16.
 */

public class TempActivity extends Activity {
    private Button ch,lbt,swq;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        ch= (Button) findViewById(R.id.button);
        lbt= (Button) findViewById(R.id.button2);
        swq= (Button) findViewById(R.id.button3);

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TempActivity.this,com.example.xdcao.diandiapp.BackUp.caohao.activity.MainActivity.class);
                startActivity(intent);
            }
        });

        lbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TempActivity.this,com.example.xdcao.diandiapp.DdService.liubotao.activity.MyListActivity.class);
                startActivity(intent);
            }
        });

        swq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TempActivity.this,com.example.xdcao.diandiapp.UI.songwenqiang.ui.MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
