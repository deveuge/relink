package com.deveuge.relink.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.deveuge.relink.api.model.entity.User;
import com.deveuge.relink.api.model.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthService {

	private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

	@Autowired
	private UserRepository userRepository;

	public Authentication getAuthentication(HttpServletRequest request) {
		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		User user = userRepository.findById(apiKey).orElse(null);
		if (apiKey == null || user == null) {
			log.error("Request to '{}' URL for non-existent user '{}'", request.getRequestURI(), apiKey);
			throw new BadCredentialsException("Invalid API Key");
		}
		return new AuthKey(user, AuthorityUtils.NO_AUTHORITIES);
	}
}