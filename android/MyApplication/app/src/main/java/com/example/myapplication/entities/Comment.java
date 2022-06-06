package com.example.myapplication.entities;

public class Comment {
    private Long id;
    private User user;
    private Post post;
    private String text;


    public Comment(Long id, User user, String text, Post post) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Post getPost() {
        return post;
    }
}
