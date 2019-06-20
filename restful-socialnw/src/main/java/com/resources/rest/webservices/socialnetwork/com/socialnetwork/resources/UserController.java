package com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources;

import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.exceptions.UserNotFoundException;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model.Post;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model.User;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.service.PostService;
import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RequestMapping(path = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    //retrieve all users
    @GetMapping(path = "/getUsers")
    public Resources<User> retrieveAllUsers() {
        ConcurrentHashMap<Integer, User> userMap = userService.getAll();

        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            Integer userId = entry.getValue().getUserId();
            List<Post> postList = postService.findPostByUserId(userId);
            if (postList.size() > 0) {
                Link link = linkTo(methodOn(UserController.class).getPostForCustomerId(userId))
                        .withRel("Posts");
                entry.getValue().add(link);
            }
        }
        Link link = linkTo(UserController.class).withSelfRel();
        return new Resources<>(new ArrayList<>(userMap.values()), link);

    }

    //retrieve a user
    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable int id) {
        User user = userService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id : " + id);
        return user;
    }

    @PostMapping(path = "/getUsers")
    public ResponseEntity<User> saveUserList(@RequestBody ConcurrentHashMap<Integer, User> userMap) {

        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            userService.save(entry.getValue());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

        User savedUser = userService.save(user);
        if (savedUser == null) {
            throw new UserNotFoundException("Not Found");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public User deleteUser(@PathVariable Integer id) {
        User deletedUser = userService.deleteById(id);
        if (deletedUser == null)
            throw new UserNotFoundException("id : " + id);

        Link link = linkTo(UserController.class).slash(id).withSelfRel();
        deletedUser.add(link);
        return deletedUser;
    }

    @GetMapping(value = "/{userId}/posts")
    public Resources<Post> getPostForCustomerId(@PathVariable Integer userId) {
        List<Post> postList = postService.findPostByUserId(userId);

        for (Post post : postList
        ) {
            Link link = linkTo(methodOn(PostController.class)
                    .getPost(post.getPostId()))
                    .withSelfRel();
            post.add(link);

        }
        Link link = linkTo(methodOn(UserController.class)
                .retrieveAllUsers())
                .withRel("allUsers");
        return new Resources<>(postList, link);

    }


}
