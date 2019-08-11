package com.resources.rest.webservices.socialnetwork.controller;

import com.resources.rest.webservices.socialnetwork.kafka.producer.Producer;
import com.resources.rest.webservices.socialnetwork.constants.Topics;
import com.resources.rest.webservices.socialnetwork.exception.exceptions.UserNotFoundException;
import com.resources.rest.webservices.socialnetwork.model.Post;
import com.resources.rest.webservices.socialnetwork.model.User;
import com.resources.rest.webservices.socialnetwork.service.PostService;
import com.resources.rest.webservices.socialnetwork.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private Producer producer;

    //retrieve all users
    @GetMapping(path = "/getUsers")
    public Resources<User> retrieveAllUsers() {
        logger.info("UserController:: retrieveAllUsers():: Get All users running");
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
        logger.info("UserController:: getUser():: getting the user for given id : " + id);
        User user = userService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id : " + id);
        return user;
    }

    @PostMapping(path = "/getUsers")
    public ResponseEntity<User> saveUserList(@RequestBody ConcurrentHashMap<Integer, User> userMap) {

        logger.info("UserController:: saveUserList():: saving userList ");

        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            User savedUser = userService.save(entry.getValue());
            producer.sendMessage(savedUser, Topics.USERS);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

        logger.info("UserController:: saveUser():: saving user : " + user.toString());
        User savedUser = userService.save(user);
        if (savedUser == null) {
            throw new UserNotFoundException("Not Found");
        }

         producer.sendMessage(savedUser, Topics.USERS);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public User deleteUser(@PathVariable Integer id) {
        logger.info("UserController:: deleteUser():: deleting user for id : " + id);
        User deletedUser = userService.deleteById(id);
        if (deletedUser == null)
            throw new UserNotFoundException("id : " + id);

        Link link = linkTo(UserController.class).slash(id).withSelfRel();
        deletedUser.add(link);
        return deletedUser;
    }

    @GetMapping(value = "/{userId}/posts")
    public Resources<Post> getPostForCustomerId(@PathVariable Integer userId) {

        logger.info("UserController:: getPostForCustomerId():: getting post for userId : " + userId);
        List<Post> postList = postService.findPostByUserId(userId);

        for (Post post : postList
        ) {
            Link link = ControllerLinkBuilder.linkTo(methodOn(PostController.class)
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
