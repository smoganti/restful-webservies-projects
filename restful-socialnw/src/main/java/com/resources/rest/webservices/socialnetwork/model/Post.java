package com.resources.rest.webservices.socialnetwork.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@ApiModel(description = "Details about the Post")
@RedisHash("Post")
public class Post extends ResourceSupport {
    private Integer postId;
    @ApiModelProperty(notes = "Post message is described here")
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

    @Override
    public String toString() {
        return "{" +
                "postId=" + postId +
                ", postMessage='" + postMessage + '\'' +
                ", postName='" + postName + '\'' +
                ", userId=" + userId +
                ", commentsList=" + commentsList +
                '}';
    }
}
