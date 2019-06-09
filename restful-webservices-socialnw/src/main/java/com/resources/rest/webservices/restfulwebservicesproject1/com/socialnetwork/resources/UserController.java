package com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources;

import com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources.dao.UserDaoService;
import com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources.exceptions.UserNotFoundException;
import com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class UserController {

    @Autowired
    private UserDaoService Userservice;

    //retrieve all users
    @GetMapping(path = "/user/getUsers")
    public ConcurrentHashMap<Integer, User> retrieveAllUsers() {
        return Userservice.getAll();
    }

    //retrieve a user
    @GetMapping(path = "/user/{id}")
    public User getUser(@PathVariable int id) {
        User user = Userservice.findOne(id);

        if(user == null)
            throw new UserNotFoundException("id : "+id);
        return user;
    }

    @PostMapping(path = "/getUsers")
    public ResponseEntity<User> saveUserList(@RequestBody ConcurrentHashMap<Integer,User> userMap) {

        for(Map.Entry<Integer,User> entry : userMap.entrySet()){
            Userservice.save(entry.getValue());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        User savedUser = Userservice.save(user);
        if (savedUser == null) {
            throw new UserNotFoundException("Not Found");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
