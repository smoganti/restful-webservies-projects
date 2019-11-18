package com.resources.rest.webservices.socialnetwork.service;

import com.resources.rest.webservices.socialnetwork.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static ConcurrentHashMap<Integer, User> userMap = new ConcurrentHashMap<>();
    private static int userCount = 3;

/*
    static {

        userMap.put(1, new User("ABC", 1, new Date()));
        userMap.put(2, new User("DEF", 2, new Date()));
        userMap.put(3, new User("GHI", 3, new Date()));

    }
*/

    public ConcurrentHashMap<Integer, User> getAll() {
        logger.info("UserService:: getAll():: Get all users : ");
        //return userRedisR
        return null;
    }

    public User save(User user) {
        logger.info("UserService:: save():: saving user : " + user.toString());
        if (user.getUserId() == null) {
            userCount++;
            user.setUserId(userCount);
        }
        userMap.put(user.getUserId(), user);

        return user;
    }


    public User findOne(int id) {
        logger.info("UserService:: findOne():: get user for id : " + id);
        return userMap.get(id);
    }

    public User deleteById(int id) {
        logger.info("UserService:: deleteById():: deleting user for id : " + id);
        User user = userMap.remove(id);
        if (user != null) {
            userCount--;
        }
        return user;
    }


}
