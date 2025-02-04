package com.deveuge.relink.kgs.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deveuge.relink.kgs.model.entity.UsedKey;

public interface UsedKeyRepository extends MongoRepository<UsedKey, String> {

}
