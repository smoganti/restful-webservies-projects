package com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources.dao;

import com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserDaoService {

    //    private static List<User> userList = new ArrayList<User>();
    private static ConcurrentHashMap<Integer, User> userMap = new ConcurrentHashMap<>();
    private static int userCount = 3;

    static {
      /*  userList.add(new User("ABC", 1, new Date()));
        userList.add(new User("DEF", 2, new Date()));
        userList.add(new User("GHI", 3, new Date()));*/

        userMap.put(1, new User("ABC", 1, new Date()));
        userMap.put(2, new User("DEF", 2, new Date()));
        userMap.put(3, new User("GHI", 3, new Date()));

    }

    public ConcurrentHashMap<Integer, User> getAll() {
        return userMap;
    }

    public User save(User user) {
        if (user.getId() == null) {
            userCount++;
            user.setId(userCount);
        }
        //         userList.add(user);
        userMap.put(user.getId(), user);

        return user;
    }


    public User findOne(int id) {
        /*for (User user : userList) {
            if (user.getId() == id)
                return user;

        }*/

        return userMap.get(id);
    }
}
