package com.example.xdcao.diandiapp.BackUp.caohao.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by xdcao on 2017/4/7.
 */

public class Supply extends BmobObject {

    private String resUserName;
    private String reqNickName;
    private String reqUserName;
    private BmobFile reqAvatar;
    private MyUser requester;
    private MyUser responsor;
    private Boolean isAccepted;

    public MyUser getRequester() {
        return requester;
    }

    public MyUser getResponsor() {
        return responsor;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setRequester(MyUser requester) {
        this.requester = requester;
    }

    public void setResponsor(MyUser responsor) {
        this.responsor = responsor;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public String getReqUserName() {
        return reqUserName;
    }

    public BmobFile getReqAvatar() {
        return reqAvatar;
    }

    public void setReqUserName(String reqUserName) {
        this.reqUserName = reqUserName;
    }

    public void setReqAvatar(BmobFile reqAvatar) {
        this.reqAvatar = reqAvatar;
    }

    public String getReqNickName() {
        return reqNickName;
    }

    public void setReqNickName(String reqNickName) {
        this.reqNickName = reqNickName;
    }

    public String getResUserName() {
        return resUserName;
    }

    public void setResUserName(String resUserName) {
        this.resUserName = resUserName;
    }
}
