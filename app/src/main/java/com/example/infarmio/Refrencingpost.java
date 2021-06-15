package com.example.infarmio;

public class Refrencingpost {
    String userid;
    String postid;

    public Refrencingpost(String userid, String postid) {
        this.userid = userid;
        this.postid = postid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}

