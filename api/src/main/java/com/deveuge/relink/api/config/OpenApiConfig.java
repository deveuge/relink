package com.deveuge.relink.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Relink API")
						.description("A URL shortening API project implemented with Spring Boot and Java 21.")
						.contact(new Contact().name("Deveuge").url("https://github.com/deveuge")));
	}
}
