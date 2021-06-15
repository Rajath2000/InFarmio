package com.example.infarmio;

import java.util.ArrayList;

public class Admin {
    String adminname;
    String password;
    String PhoneNumber;
    String profileurl;

    ArrayList<Post> post;
    ArrayList<Refrencingpost> spost;
    ArrayList<String> Myfavratious;

    public ArrayList<String> getMyfavratious() {
        return Myfavratious;
    }

    public void setMyfavratious(ArrayList<String> myfavratious) {
        Myfavratious = myfavratious;
    }

    public Admin(String adminname, String password, String phoneNumber, String profileurl, ArrayList<Post> post, ArrayList<Refrencingpost> spost, ArrayList<String> myfavratious) {
        this.adminname = adminname;
        this.password = password;
        PhoneNumber = phoneNumber;
        this.profileurl = profileurl;
        this.post = post;
        this.spost = spost;
        Myfavratious = myfavratious;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public ArrayList<Post> getPost() {
        return post;
    }

    public void setPost(ArrayList<Post> post) {
        this.post = post;
    }

    public ArrayList<Refrencingpost> getSpost() {
        return spost;
    }

    public void setSpost(ArrayList<Refrencingpost> spost) {
        this.spost = spost;
    }
}

