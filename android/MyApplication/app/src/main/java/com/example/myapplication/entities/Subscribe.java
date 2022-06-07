package com.example.myapplication.entities;

public class Subscribe {
    private Long id;
    private User profile;
    private User subscriber;

    public Subscribe(Long id, User profile, User subscriber){
        this.id = id;
        this.profile = profile;
        this.subscriber = subscriber;
    }

    public Long getId() {
        return id;
    }

    public User getProfile() {
        return profile;
    }

    public User getSubscriber() {
        return subscriber;
    }
}
