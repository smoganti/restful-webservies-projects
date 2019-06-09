package com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources;

import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.dao.PostDaoService;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.exceptions.PostNotFoundException;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PostController {

    @Autowired
    private PostDaoService postDaoService;

    @GetMapping(path = "/post/getPosts")
    public ConcurrentHashMap<Integer, Post> retrieveAllPosts(){
        return postDaoService.getAll();
    }

    @GetMapping(path = "/post/{id}")
    public Post getPost(@PathVariable Integer id){
        return postDaoService.findOne(id);
    }

    @PostMapping(path = "/post/getPosts")
    public ResponseEntity<Object> saveAllPosts(@RequestBody ConcurrentHashMap<Integer,Post> postMap){
        for(Map.Entry<Integer,Post> entry:postMap.entrySet()){
            postDaoService.save(entry.getValue());

        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping(value = "/post")
    public ResponseEntity<Post> savePost(@RequestBody Post post){
        Post savedPost = postDaoService.save(post);

        if(savedPost == null){
            throw new PostNotFoundException("Post Not Found");
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getPostId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
