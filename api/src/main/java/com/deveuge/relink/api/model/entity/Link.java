package com.deveuge.relink.api.model.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Document("link")
@AllArgsConstructor
@Getter
@Builder
public class Link {

	@Id
	private String hash;
	@Indexed
	private String originalURL;
	private User user;
	private Date expirationDate;

	public boolean isExpired() {
		return this.expirationDate != null && new Date().after(this.expirationDate);
	}

	public boolean isOwner(User user) {
		return this.getUser().getApiKey().equals(user.getApiKey());
	}

}
