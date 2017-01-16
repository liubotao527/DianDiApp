package com.example.xdcao.diandiapp.BackUp.usermanage;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xdcao on 2017/1/16.
 */

public class Post extends BmobObject {

    private String title;
    private String content;
    private MyUser author;
    private BmobFile image;
    private BmobRelation likes;

    public Post(String title, String content, MyUser author) {
        this.title = title;
        this.content = content;
        this.author = author;
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

    public BmobFile getImage() {
        return image;
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

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }
}
