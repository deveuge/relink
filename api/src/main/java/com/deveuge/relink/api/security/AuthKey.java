package com.deveuge.relink.api.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.deveuge.relink.api.model.entity.User;

public class AuthKey extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -5003211108216226033L;
	private final User user;

	public AuthKey(User user, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.user = user;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}
}