package com.example.xdcao.diandiapp.BackUp.usermanage;

import cn.bmob.v3.BmobObject;

/**
 * Created by xdcao on 2017/1/16.
 */

public class Comment extends BmobObject {

    private String content;
    private MyUser user;
    private Post post;

    public String getContent() {
        return content;
    }

    public MyUser getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
