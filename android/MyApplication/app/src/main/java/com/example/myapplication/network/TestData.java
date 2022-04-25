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
                "http://i.imgur.com/DvpvklR.png",
                "Польователь1",
                "Ник1",
                "Sample description",
                "Россия",
                3,
                5
        );
    }
    public static User getUser1() {
        return new User(
                1L,
                "http://i.imgur.com/DvpvklR.png",
                "Польователь2",
                "Ник2",
                "Sample description",
                "Испания",
                3,
                5
        );
    }
    public static User getUser2() {
        return new User(
                1L,
                "http://i.imgur.com/DvpvklR.png",
                "Польователь3",
                "Ник3",
                "Sample description",
                "Грузия",
                3,
                5
        );
    }

    public static Collection<Comment> getComments() {
        return Arrays.asList(
                new Comment(getUser(), "Comment1", "Thu Dec 11 07:31:08 +0000 2017"),

                new Comment(getUser(), "Comment2", "Thu Dec 11 07:31:08 +0000 2017"),

                new Comment(getUser(), "Comment3", "Thu Dec 11 07:31:08 +0000 2017")
        );
    }

    public static Collection<Post> getTweets() {
        return Arrays.asList(
                new Post(getUser(), 1L, "Thu Dec 13 07:31:08 +0000 2017", "Очень длинное описание твита 1\nблаблабла",
                        4L, 4L),

                new Post(getUser1(), 2L, "Thu Dec 12 07:31:08 +0000 2017", "Очень длинное описание твита 2\nблаблабла\nблаблабла",
                        5L, 5L),

                new Post(getUser2(), 3L, "Thu Dec 11 07:31:08 +0000 2017", "Очень длинное описание твита 3\nблаблабла",
                        6L, 6L),

                new Post(getUser(), 3L, "Thu Dec 11 07:31:08 +0000 2017", "Очень длинное описание твита 4",
                        6L, 6L)
        );
    }
}
