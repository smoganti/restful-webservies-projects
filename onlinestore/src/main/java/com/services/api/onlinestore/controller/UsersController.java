package com.services.api.onlinestore.controller;

import com.services.api.onlinestore.dao.MySQLDataStoreUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users")
public class UsersController {

    @Autowired
    MySQLDataStoreUtilities mySQLDataStoreUtilities;
}
