package com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.service;

import com.resources.rest.webservices.socialnetwork.com.socialnetwork.resources.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserService {

    private static ConcurrentHashMap<Integer, User> userMap = new ConcurrentHashMap<>();
    private static int userCount = 3;

    static {

        userMap.put(1, new User("ABC", 1, new Date()));
        userMap.put(2, new User("DEF", 2, new Date()));
        userMap.put(3, new User("GHI", 3, new Date()));

    }

    public ConcurrentHashMap<Integer, User> getAll() {
        return userMap;
    }

    public User save(User user) {
        if (user.getUserId() == null) {
            userCount++;
            user.setUserId(userCount);
        }
        userMap.put(user.getUserId(), user);

        return user;
    }


    public User findOne(int id) {

        return userMap.get(id);
    }

    public User deleteById(int id) {

        User user = userMap.remove(id);
        if (user != null) {
            userCount--;
        }
        return user;
    }


}
