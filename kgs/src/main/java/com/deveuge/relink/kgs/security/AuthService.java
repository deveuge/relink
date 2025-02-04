package com.deveuge.relink.kgs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.deveuge.relink.kgs.model.repository.APIKeyRepository;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthService {

	private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

	@Autowired
	private APIKeyRepository apiKeyRepository;

	public Authentication getAuthentication(HttpServletRequest request) {
		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		if (apiKey == null || apiKeyRepository.findById(apiKey).isEmpty()) {
			throw new BadCredentialsException("Invalid API Key");
		}
		return new AuthKey(apiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}