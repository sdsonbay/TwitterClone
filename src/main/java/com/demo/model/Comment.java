package com.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    private User commentUserId;
    @ManyToOne
    private Tweet commentTweetId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(User commentUserId) {
        this.commentUserId = commentUserId;
    }

    public Tweet getCommentTweetId() {
        return commentTweetId;
    }

    public void setCommentTweetId(Tweet commentTweetId) {
        this.commentTweetId = commentTweetId;
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
}
