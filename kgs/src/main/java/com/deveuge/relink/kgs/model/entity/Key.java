package com.deveuge.relink.kgs.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Document("keys")
@AllArgsConstructor
@Getter
public class Key {

	@Id
	private String key;
}
