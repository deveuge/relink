package com.deveuge.relink.kgs.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthKey extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 2937783562227409776L;
	private final String apiKey;

	public AuthKey(String apiKey, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.apiKey = apiKey;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return apiKey;
	}
}