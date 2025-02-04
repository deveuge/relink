package com.deveuge.relink.kgs.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deveuge.relink.kgs.model.entity.APIKey;

public interface APIKeyRepository extends MongoRepository<APIKey, String> {
}
