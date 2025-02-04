package com.deveuge.relink.api.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deveuge.relink.api.config.KGSConfiguration;

@Service
public class KGSServiceImpl implements KGSService {

	@Autowired
	private KGSConfiguration kgsConfiguration;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String getHash() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(KGSConfiguration.AUTH_TOKEN_HEADER_NAME, kgsConfiguration.getKey());
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(kgsConfiguration.getGetURL(), HttpMethod.GET, entity,
				String.class);
		return response.getBody();
	}

	@Override
	public void deleteHashes(List<String> ids) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(KGSConfiguration.AUTH_TOKEN_HEADER_NAME, kgsConfiguration.getKey());
		HttpEntity<Object> entity = new HttpEntity<>(ids, headers);
		restTemplate.exchange(kgsConfiguration.getDeleteURL(), HttpMethod.DELETE, entity, String.class);
	}

}
