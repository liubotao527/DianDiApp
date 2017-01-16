package com.example.xdcao.diandiapp.BackUp.usermanage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xdcao on 2017/1/16.
 */

public class PostActivity extends Activity {

    private static final String TAG = "PostActivity";

    private Button post_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        post_btn=(Button)findViewById(R.id.post_btn);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPost();
            }
        });
    }

    public static void userPost(){
        MyUser user= BmobUser.getCurrentUser(MyUser.class);
        Post post=new Post("第一个帖子","这是我的第一个帖子",user);
        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Log.i(TAG, "done: "+"帖子发布成功");
                }else {
                    Log.i(TAG, "done: "+"帖子发布失败");
                }
            }
        });

    }

}
