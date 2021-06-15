package com.example.infarmio;

import java.util.ArrayList;

public class User {
    String Username;
    String Password;
    ArrayList<String> Myfavratious;
    String phone;
    String Profileurl;
    ArrayList<Post> post;
    ArrayList<Refrencingpost> spost;

    public User(String username, String password, ArrayList<String> myfavratious, String phone, String profileurl, ArrayList<Post> post, ArrayList<Refrencingpost> spost) {
        Username = username;
        Password = password;
        Myfavratious = myfavratious;
        this.phone = phone;
        Profileurl = profileurl;
        this.post = post;
        this.spost = spost;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ArrayList<String> getMyfavratious() {
        return Myfavratious;
    }

    public void setMyfavratious(ArrayList<String> myfavratious) {
        Myfavratious = myfavratious;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileurl() {
        return Profileurl;
    }

    public void setProfileurl(String profileurl) {
        Profileurl = profileurl;
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
