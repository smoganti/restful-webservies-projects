package com.resources.rest.webservices.socialnetwork.dao;

import com.resources.rest.webservices.socialnetwork.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedisRepository extends CrudRepository<User, String> {
}
