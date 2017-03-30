package com.example.xdcao.diandiapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyDdNote implements Serializable {
    private static final long serialVersionUID = 2189052605715370758L;
    public String note;
    public List<String> urlList = new ArrayList<>();
    public boolean isShowAll = false;
    public String time;


    public String getNote() {
        return note;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public String getTime() {
        return time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
