package com.example.xdcao.diandiapp.BackUp.caohao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Post;
import com.example.xdcao.diandiapp.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xdcao on 2017/1/16.
 */

public class PostActivity extends BaseActivity {

    private static final String TAG = "PostActivity";

    private EditText post_text;
    private EditText post_title;
    private Button post_btn;
    private Button logout_btn;
    private Button toFriendsAcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        initActivity();
    }

    private void initActivity() {
        post_btn=(Button)findViewById(R.id.post_btn);
        logout_btn=(Button)findViewById(R.id.logout_btn);
        toFriendsAcBtn=(Button)findViewById(R.id.toFriendsAc);
        post_text=(EditText)findViewById(R.id.post_text_input);
        post_title=(EditText)findViewById(R.id.title_input);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPost();
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });
        toFriendsAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PostActivity.this,FriendsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLogout() {
        BmobUser.logOut();
        BmobUser currentUser=BmobUser.getCurrentUser();
        Intent intent=new Intent(PostActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void userPost(){
        MyUser user= BmobUser.getCurrentUser(MyUser.class);
        Post post=new Post(post_title.getText().toString(),post_text.getText().toString(),user);
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
