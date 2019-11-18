package com.resources.rest.webservices.socialnetwork.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Comments")
public class Comments {
    private String commentMessage;

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }
}
