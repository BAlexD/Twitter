package com.example.myapplication.entities;

public class Like {
    private Long id;
    private User user;
    private Post post;

    public Like(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }
}
