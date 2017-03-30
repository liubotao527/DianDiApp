package com.example.xdcao.diandiapp.DdService.liubotao.database;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-3-2.
 */

public class DiandiNote {
    private int id;
    private String content;
    private String date;
    private String time;
    private ArrayList<String> imgs;

    public DiandiNote(int id, String content, String date, String time, ArrayList<String> imgs) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
        this.imgs = imgs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }
}
