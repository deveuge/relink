package com.deveuge.relink.kgs.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Document("api-keys")
@AllArgsConstructor
@Getter
public class APIKey {

	@Id
	private String key;
}
