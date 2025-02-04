package com.deveuge.relink.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "key.generator.service")
@Getter
@Setter
public class KGSConfiguration {

	public static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

	private String url;
	private String key;

	public String getGetURL() {
		return this.url + "/get";
	}

	public String getDeleteURL() {
		return this.url + "/delete";
	}
}
