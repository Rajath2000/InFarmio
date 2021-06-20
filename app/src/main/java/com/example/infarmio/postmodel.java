package com.example.infarmio;

public class postmodel {
    String catagory;
    String contactNumber;
    String image;
    String postid;
    String probem;
    String profileurl;
    String reference;
   String solution;
    String title;
    String username;


    public postmodel() {
    }

    public postmodel(String catagory, String contactNumber, String image, String postid, String probem, String profileurl, String reference, String solution, String title, String username) {
        this.catagory = catagory;
        this.contactNumber = contactNumber;
        this.image = image;
        this.postid = postid;
        this.probem = probem;
        this.profileurl = profileurl;
        this.reference = reference;
        this.solution = solution;
        this.title = title;
        this.username = username;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getProbem() {
        return probem;
    }

    public void setProbem(String probem) {
        this.probem = probem;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

