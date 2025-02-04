package com.deveuge.relink.api.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.deveuge.relink.api.model.entity.Link;

public interface LinkRepository extends MongoRepository<Link, String> {

	@Query(value = "{'expirationDate': {'$lte' : new Date()}}")
	List<Link> findExpired();

	@Query("{'originalURL' :?0}")
	Optional<Link> findByOriginalURL(String originalURL);
}
