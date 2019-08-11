package com.resources.rest.webservices.socialnetwork.service;

import com.resources.rest.webservices.socialnetwork.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private static ConcurrentHashMap<Integer, Post> postMap = new ConcurrentHashMap<>();
    private static Integer postCount = 3;

/*
    static{
        postMap.put(1,new Post(1,"Hello! This is a Post","Hello",1,null));
        postMap.put(2,new Post(2,"Second line of a Post","Introduction",1,null));
        postMap.put(3,new Post(3,"Hello! This is a Post","Hello",2,null));
    }
*/

    public ConcurrentHashMap<Integer,Post> getAll(){
        logger.info("PostService:: getAll():: Get all posts : ");
        return postMap;
    }

    public Post findOne(Integer id){
        logger.info("PostService:: findOne():: get post for id : " + id);
        return postMap.get(id);
    }

    public Post save(Post post){
        logger.info("PostService:: save():: saving post : " + post.toString());
        if(post.getPostId() == null){
            post.setPostId(postCount++);
        }
        postMap.put(post.getPostId(),post);
        return post;
    }

    public Post deleteById(int id){
        logger.info("PostService:: deleteById():: deleting post for id : " + id);
        return postMap.remove(id);
    }

    public List<Post> findPostByUserId(Integer userId){

        logger.info("PostService:: findPostByUserId():: find post for userId: " + userId);
        List<Post> postList = new ArrayList<>();
        for(Map.Entry<Integer,Post> entry:postMap.entrySet()){
            if(entry.getValue().getUserId().equals(userId)){
                postList.add(entry.getValue());
            }
        }
        return postList;

    }


}
