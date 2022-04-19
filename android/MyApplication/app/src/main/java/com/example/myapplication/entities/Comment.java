package com.example.myapplication.entities;

public class Comment {
    private User user;
    private String text;
    private String creationDate;

    public Comment(User user, String text, String creationDate) {
        this.user = user;
        this.text = text;
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
    public String getCreationDate() {
        return creationDate;
    }

}
