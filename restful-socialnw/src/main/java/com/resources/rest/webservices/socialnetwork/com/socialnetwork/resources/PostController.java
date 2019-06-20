package com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources;

import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.exceptions.PostNotFoundException;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model.Post;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(path = "/getPosts")
    public ConcurrentHashMap<Integer, Post> retrieveAllPosts() {
        return postService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable Integer id) {
        return postService.findOne(id);
    }

    @PostMapping(path = "/getPosts")
    public ResponseEntity<Object> saveAllPosts(@RequestBody ConcurrentHashMap<Integer, Post> postMap) {
        for (Map.Entry<Integer, Post> entry : postMap.entrySet()) {
            postService.save(entry.getValue());

        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping(value = "")
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        Post savedPost = postService.save(post);

        if (savedPost == null) {
            throw new PostNotFoundException("Post Not Found");
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getPostId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public void deletePost(@PathVariable Integer id) {
        Post post = postService.deleteById(id);

        if (post == null) {
            throw new PostNotFoundException(" id : " + id);
        }

    }

}