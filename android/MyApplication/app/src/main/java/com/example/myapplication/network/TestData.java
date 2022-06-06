package com.example.myapplication.network;

import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.Arrays;
import java.util.Collection;

public class TestData {
    public static User getUser() {
        return new User(
                1L,
                "Ник1",
                3L,
                5L
        );
    }
    public static User getUser1() {
        return new User(
                1L,
                "Ник2",
                3L,
                5L
        );
    }
    public static User getUser2() {
        return new User(
                1L,
                "Ник3",
                3L,
                5L
        );
    }

    public static Post getPost() {
        return new Post(1L,getUser(),   "Очень длинное описание твита 1",
                4L, 4L
        );
    }
    public static Collection<Comment> getComments() {
        return Arrays.asList(
                new Comment(1L, getUser(), "Comment1", getPost()),

                new Comment(2L, getUser(), "Comment2", getPost()),

                new Comment(3L, getUser(), "Comment3", getPost())
        );
    }


    public static Collection<Post> getTweets() {
        return Arrays.asList(
                new Post(1L,getUser(),   "Очень длинное описание твита 1",
                        4L, 4L),

                new Post(2L,getUser1(),   "Очень длинное описание твита 2",
                        5L, 5L),

                new Post(3L,  getUser2(), "Очень длинное описание твита 3",
                        6L, 6L),

                new Post(3L,getUser(),   "Очень длинное описание твита 4",
                        6L, 6L)
        );
    }
}
