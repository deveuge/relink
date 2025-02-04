package com.deveuge.relink.kgs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "kgs.generator")
@Getter
@Setter
public class GeneratorConfiguration {

	private String alphabet;
	private int length;
	private int quantity;

}
