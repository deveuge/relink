package com.deveuge.relink.app.model.dto;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

@Getter
public class LinkRequestDTO {

	private String originalURL;
	private Date expirationDate;

	public LinkRequestDTO(String originalURL) {
		super();
		this.originalURL = originalURL;
		this.expirationDate = getDefaultExpirationDate();
	}

	private Date getDefaultExpirationDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, +1);
		return calendar.getTime();
	}

}
