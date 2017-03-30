package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.prefs.UserPrefs;


public class SplashActivity extends AppCompatActivity {

    private final static int JUMP_TO_NEXT = 1;

    private MyHandler handler = new MyHandler(SplashActivity.this);

    public UserPrefs userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userPrefs = new UserPrefs(SplashActivity.this);
        handler.sendEmptyMessageDelayed(JUMP_TO_NEXT,1000);
    }

    private  class MyHandler extends Handler {
        Activity activity;
        MyHandler(Activity activity){
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case JUMP_TO_NEXT:

                    if(!TextUtils.isEmpty(userPrefs.getAuthToken())){
                        activity.startActivity(new Intent(activity,MainActivity.class));
                    }else{
                        activity.startActivity(new Intent(activity,SignUpActivity.class));
                    }
                    activity.finish();
            }
        }
    }
}
