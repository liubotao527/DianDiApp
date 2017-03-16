package com.example.xdcao.diandiapp.BackUp.caohao.bean;

import android.provider.ContactsContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xdcao on 2017/1/16.
 */

public class MyUser extends BmobUser {

    private Boolean sex;//1:male,0:female
    private Integer age;
    private BmobRelation friends;


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
}
