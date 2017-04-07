package com.example.xdcao.diandiapp.UI.songwenqiang.bean;

import android.graphics.Bitmap;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by wewarrios on 2017/4/5.
 */

public class ContactItem {
    //个人头像
    private BmobFile avatar;

    //个人昵称
    private String nickName;

    //个性签名

    private String signName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }
}
