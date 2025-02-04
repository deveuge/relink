package com.deveuge.relink.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deveuge.relink.api.security.AuthFilter;
import com.deveuge.relink.api.security.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private AuthService authService;

	@Bean
	AuthFilter authFilter() {
		return AuthFilter.builder().authService(authService).build();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
								.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().requestMatchers("/**")
								.authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}