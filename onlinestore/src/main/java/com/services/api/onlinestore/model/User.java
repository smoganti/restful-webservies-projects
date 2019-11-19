package com.services.api.onlinestore.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    String firstName;
    String lastName;
    String userId;
    String password;
    String userType;
    String rePassword;


}
