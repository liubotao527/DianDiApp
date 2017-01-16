package com.example.xdcao.diandiapp;

import cn.bmob.v3.BmobObject;

/**
 * Created by xdcao on 2017/1/14.
 */

public class Lost extends BmobObject {

    private String title;
    private String describe;
    private String phone;

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescribe() {
        return describe;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
