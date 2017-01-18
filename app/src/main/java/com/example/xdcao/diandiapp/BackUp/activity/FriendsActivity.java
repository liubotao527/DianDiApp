package com.example.xdcao.diandiapp.BackUp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.xdcao.diandiapp.BackUp.adapters.FriendAdapter;
import com.example.xdcao.diandiapp.BackUp.bean.MyUser;
import com.example.xdcao.diandiapp.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xdcao on 2017/1/17.
 */

public class FriendsActivity extends BaseActivity {

    private ListView friendList;

    private FriendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);
        initActivity();
        queryUsers();
    }

    private void queryUsers() {
        BmobQuery<MyUser> query=new BmobQuery<MyUser>();
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e==null){
                    friendAdapter=new FriendAdapter(FriendsActivity.this,list);
                    friendList.setAdapter(friendAdapter);
                    System.out.print("查询用户成功： 用户数： "+list.size());
                }else {
                    System.out.print("查询用户失败");
                }
            }
        });
    }

    private void initActivity() {
        friendList=(ListView)findViewById(R.id.friend_list);
    }


}
