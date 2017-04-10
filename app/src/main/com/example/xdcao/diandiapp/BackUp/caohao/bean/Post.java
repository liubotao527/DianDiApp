package com.example.xdcao.diandiapp.BackUp.caohao.bean;

import java.lang.reflect.Array;
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
    private String authorName;
    private List<String> images;
    private List<String> filenames;
    private BmobRelation likes;
    private BmobDate createDate;
    private Boolean isShared;

    public Post(){}

    public String getAuthorName() {
        return authorName;
    }

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
        this.authorName=author.getNickName();
    }


    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
