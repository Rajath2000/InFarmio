package com.example.infarmio;

import java.util.ArrayList;

public class Post {
    //    String postid;
    String title;
    String catagory;
    String decription;
    String problem;
    String method;
    String usage;
    String usertips;
    String contact;
    ArrayList<String> postImgUrl;
    String postpdfUrl;
    int likes;
    int dislikes;
    String status;

    public Post(String title, String catagory, String decription, String problem, String method, String usage, String usertips, String contact, ArrayList<String> postImgUrl, String postpdfUrl, int likes, int dislikes, String status) {
        this.title = title;
        this.catagory = catagory;
        this.decription = decription;
        this.problem = problem;
        this.method = method;
        this.usage = usage;
        this.usertips = usertips;
        this.contact = contact;
        this.postImgUrl = postImgUrl;
        this.postpdfUrl = postpdfUrl;
        this.likes = likes;
        this.dislikes = dislikes;
        this.status = status;
    }
    //    public String getPostid() {
//        return postid;
//    }
//
//    public void setPostid(String postid) {
//        this.postid = postid;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsertips() {
        return usertips;
    }

    public void setUsertips(String usertips) {
        this.usertips = usertips;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ArrayList<String> getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(ArrayList<String> postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getPostpdfUrl() {
        return postpdfUrl;
    }

    public void setPostpdfUrl(String postpdfUrl) {
        this.postpdfUrl = postpdfUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
