package com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.dao;

import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model.Post;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostDaoService {
    private static ConcurrentHashMap<Integer, Post> postMap = new ConcurrentHashMap<>();
    private static Integer postCount = 3;

    static{
        postMap.put(1,new Post(1,"Hello! This is a Post","Hello",1,null));
        postMap.put(2,new Post(2,"Second line of a Post","Introduction",1,null));
        postMap.put(3,new Post(3,"Hello! This is a Post","Hello",2,null));
    }

    public ConcurrentHashMap<Integer,Post> getAll(){
        return postMap;
    }

    public Post findOne(Integer id){
        return postMap.get(id);
    }

    public Post save(Post post){
        if(post.getPostId() == null){
            post.setPostId(postCount++);
        }
        postMap.put(post.getPostId(),post);
        return post;
    }


}
