package com.deveuge.relink.api.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LinkRequestDTO {

	private String originalURL;
	private Date expirationDate;

}
