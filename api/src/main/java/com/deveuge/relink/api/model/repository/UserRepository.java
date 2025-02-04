package com.deveuge.relink.api.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deveuge.relink.api.model.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

}
