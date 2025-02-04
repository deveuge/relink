package com.deveuge.relink.app.model.service;

import org.springframework.web.client.RestClientException;

import com.deveuge.relink.app.model.dto.LinkRequestDTO;

public interface APIService {

	String createUrl(LinkRequestDTO request) throws RestClientException;

	String getUrl(String hash) throws RestClientException;
}
