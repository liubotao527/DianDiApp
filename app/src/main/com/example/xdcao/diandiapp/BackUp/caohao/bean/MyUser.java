package com.example.xdcao.diandiapp.BackUp.caohao.bean;

import android.provider.ContactsContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xdcao on 2017/1/16.
 */

public class MyUser extends BmobUser {

    private Boolean sex;//1:male,0:female
    private Integer age;
    private String nickName;
    private BmobRelation friends;
    private BmobRelation unchecked;
    private BmobFile avatar;
    private String signName;


    public Boolean getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BmobRelation getFriends() {
        return friends;
    }

    public void setFriends(BmobRelation friends) {
        this.friends = friends;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobRelation getUnchecked() {
        return unchecked;
    }

    public void setUnchecked(BmobRelation unchecked) {
        this.unchecked = unchecked;
    }

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
}
