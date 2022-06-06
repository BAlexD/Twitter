package com.example.myapplication.entities;

public class User {
    private Long id;
    private String nick;
    private Long followingCount;
    private Long followersCount;

    public User(Long id,
                String nick,
                Long followingCount,
                Long followersCount) {
        this.id = id;
        this.nick = nick;

        this.followingCount = followingCount;
        this.followersCount = followersCount;
    }

    public long getId() {
        return id;
    }


    public String getNick() {
        return nick;
    }


    public Long getFollowingCount() {
        return followingCount;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (followingCount != user.followingCount) return false;
        if (followersCount != user.followersCount) return false;
        if (!nick.equals(user.nick)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + nick.hashCode();
        result = 31 * result + (int)(followingCount^ (followingCount >>> 32));
        result = 31 * result + (int)(followersCount^ (followersCount >>> 32));
        return result;
    }
}
