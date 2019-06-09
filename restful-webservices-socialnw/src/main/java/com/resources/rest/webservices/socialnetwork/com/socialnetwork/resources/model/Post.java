package com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model;

import java.util.List;

public class Post {
    private Integer postId;
    private String postMessage;
    private String postName;
    private Integer userId;
    private List<Comments> commentsList;

    public Post(Integer postId, String postMessage, String postName, Integer userId, List<Comments> commentsList) {
        this.postId = postId;
        this.postMessage = postMessage;
        this.postName = postName;
        this.userId = userId;
        this.commentsList = commentsList;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }
}
