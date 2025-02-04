package com.deveuge.relink.app.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.deveuge.relink.app.config.APIConfiguration;
import com.deveuge.relink.app.model.dto.LinkRequestDTO;

@Service
public class APIServiceImpl implements APIService {

	@Autowired
	private APIConfiguration relinkConfiguration;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String createUrl(LinkRequestDTO request) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(APIConfiguration.AUTH_TOKEN_HEADER_NAME, relinkConfiguration.getKey());
		HttpEntity<LinkRequestDTO> requestEntity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = restTemplate.exchange(relinkConfiguration.getCreateURL(), HttpMethod.POST,
				requestEntity, String.class);
		return response.getBody();
	}

	@Override
	public String getUrl(String hash) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(APIConfiguration.AUTH_TOKEN_HEADER_NAME, relinkConfiguration.getKey());
		HttpEntity<LinkRequestDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(relinkConfiguration.getGetURL(hash), HttpMethod.GET,
				requestEntity, String.class);
		return response.getBody();
	}

}
