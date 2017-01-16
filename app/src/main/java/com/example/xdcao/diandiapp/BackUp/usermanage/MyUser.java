package com.example.xdcao.diandiapp.BackUp.usermanage;

import cn.bmob.v3.BmobUser;

/**
 * Created by xdcao on 2017/1/16.
 */

public class MyUser extends BmobUser {

    private Boolean sex;//1:male,0:female
    private Integer age;


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

}
