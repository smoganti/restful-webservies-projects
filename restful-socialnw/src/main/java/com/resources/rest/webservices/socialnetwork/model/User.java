package com.resources.rest.webservices.socialnetwork.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel(description = "Details about the User")
@RedisHash("User")
public class User extends ResourceSupport {

    public User(){}

    public User(@Size(min = 2, max = 30, message = "Minimum 2 characters and Maximum 30 only") String name, Integer userId, @Past(message = "Date must a valid Past Date") Date dateOfBirth) {
        this.name = name;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
    }

    @Size (min = 2,max = 30,message = "Minimum 2 characters and Maximum 30 only")
    @ApiModelProperty(notes = "Name must be witin 2 characters and 30 characters only")
    private String name;
    private Integer userId;
    @Past(message = "Date must a valid Past Date")
    @ApiModelProperty(notes = "Cannot be a past Date")
    private Date dateOfBirth;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
