package com.example.xdcao.diandiapp.BackUp.caohao.bean;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xdcao on 2017/1/16.
 */

public class Post extends BmobObject {

    private String title;
    private String content;
    private MyUser author;
    private List<BmobFile> images;
    private BmobRelation likes;
    private BmobDate createDate;
    private Boolean isShared;

    public Post(){}

    public Post(String title, String content, MyUser author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate=new BmobDate(new Date());
    }

    public void setCreateDate(BmobDate createDate) {
        this.createDate = createDate;
    }

    public BmobDate getCreateDate() {

        return createDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public MyUser getAuthor() {
        return author;
    }


    public BmobRelation getLikes() {
        return likes;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }


    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public List<BmobFile> getImages() {
        return images;
    }

    public void setImages(List<BmobFile> images) {

        this.images = images;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }
}
