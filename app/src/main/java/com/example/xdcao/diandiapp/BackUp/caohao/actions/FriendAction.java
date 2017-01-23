package com.example.xdcao.diandiapp.BackUp.caohao.actions;

import android.util.Log;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xdcao on 2017/1/23.
 */

public class FriendAction {

    private static final String TAG = "FriendAction";

    /*
    添加好友
     */
    public static void addFriend(MyUser friend){
        MyUser user= BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation=new BmobRelation();
        relation.add(friend);
        user.setFriends(relation);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i(TAG, "多对多关联添加成功");;
                }else {
                    Log.i(TAG, "失败"+e.getMessage());
                }
            }
        });
    }


    /*
    查询好友
     */
    public static List<MyUser> queryFriends(){
        BmobQuery<MyUser> query=new BmobQuery<MyUser>();
        MyUser me=BmobUser.getCurrentUser(MyUser.class);
        query.addWhereRelatedTo("friends",new BmobPointer(me));
        final List<MyUser>[] friendList = null;
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e==null){
                    friendList[0] =list;
                    Log.i(TAG, "查询个数："+ list.size());
                }else {
                    Log.i(TAG, "失败"+e.getMessage());
                }
            }
        });
        return friendList[0];
    }

}
