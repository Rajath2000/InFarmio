package com.example.infarmio;

import java.util.ArrayList;

public class Post {
    String postid;
    String profileurl;
    String username;
    String Title;
    String Probem;
    String Solution;
    String Catagory;
    String ContactNumber;
    String Reference;
    String Image;

    public Post(String postid, String profileurl, String username, String title, String probem, String solution, String catagory, String contactNumber, String reference, String image) {
        this.postid = postid;
        this.profileurl = profileurl;
        this.username = username;
        Title = title;
        Probem = probem;
        Solution = solution;
        Catagory = catagory;
        ContactNumber = contactNumber;
        Reference = reference;
        Image = image;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProbem() {
        return Probem;
    }

    public void setProbem(String probem) {
        Probem = probem;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public String getCatagory() {
        return Catagory;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
