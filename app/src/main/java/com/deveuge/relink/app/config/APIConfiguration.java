package com.deveuge.relink.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "relink.api")
@Getter
@Setter
public class APIConfiguration {

	public static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

	private String url;
	private String key;

	public String getCreateURL() {
		return url + "/create";
	}

	public String getGetURL(String hash) {
		return url + "/get/" + hash;
	}

}
