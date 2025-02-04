package com.deveuge.relink.api.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Document("user")
@AllArgsConstructor
@Getter
@Builder
public class User {

	@Id
	private String apiKey;
	private String name;
	private String email;

}
