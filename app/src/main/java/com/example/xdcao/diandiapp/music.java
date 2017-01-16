package com.example.xdcao.diandiapp;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xdcao on 2017/1/16.
 */

public class music extends BmobObject {

    private String name;
    private String artist;
    private BmobFile mp3;

    public music(String name, String artist,BmobFile bmobFile) {
        this.name=name;
        this.artist=artist;
        this.mp3=bmobFile;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public BmobFile getMp3() {
        return mp3;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setMp3(BmobFile mp3) {
        this.mp3 = mp3;
    }
}
