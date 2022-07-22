package com.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @ManyToOne
    private User tweetUser;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_tweet_id_tweetid")
    List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getTweetUser() {
        return tweetUser;
    }

    public void setTweetUser(User tweetUser) {
        this.tweetUser = tweetUser;
    }
}
