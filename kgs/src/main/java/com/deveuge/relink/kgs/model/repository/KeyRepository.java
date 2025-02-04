package com.deveuge.relink.kgs.model.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.deveuge.relink.kgs.model.entity.Key;

public interface KeyRepository extends MongoRepository<Key, String> {

	@Aggregation(pipeline = { "{ '$limit' : 1 }" })
	Key findOne();
}
