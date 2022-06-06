package com.example.myapplication.entities;

public class Post {
    private User user;
    private Long id;
    private String text;
    private Long commentCount;
    private Long favouriteCount;
    //private String imageUrl;

    public Post(Long id, User user,   String text,
                 Long commentCount, Long favouriteCount) {//, String imageUrl) {
        this.user = user;
        this.id = id;
        this.text = text;
        this.commentCount = commentCount;
        this.favouriteCount = favouriteCount;
        //this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getСommentCount() {
        return commentCount;
   }

    public Long getFavouriteCount() {
        return favouriteCount;
    }

    /*public String getImageUrl() {
        return imageUrl;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post tweet = (Post) o;

        if (!user.equals(tweet.user)) return false;
        if (!id.equals(tweet.id)) return false;
        if (!text.equals(tweet.text)) return false;
        if (!commentCount.equals(tweet.commentCount)) return false;
        if (!favouriteCount.equals(tweet.favouriteCount)) return false;
        return true;
        //return imageUrl != null ? imageUrl.equals(tweet.imageUrl) : tweet.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + commentCount.hashCode();
        result = 31 * result + favouriteCount.hashCode();
        //result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

}
