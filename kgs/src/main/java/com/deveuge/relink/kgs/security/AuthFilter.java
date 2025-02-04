package com.deveuge.relink.kgs.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;

/**
 * Custom filter for API key validation of requests
 */
@Builder
public class AuthFilter extends GenericFilterBean {

	AuthService authService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			Authentication authentication = authService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception ex) {
			buildErrorResponse((HttpServletResponse) response, ex);
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Builds the HTTP error response if the user is not authorized or an error
	 * occurs during authorization validation.
	 * 
	 * @param httpResponse {@link HttpServletResponse} The response associated with
	 *                     the request
	 * @param ex           {@link Exception} The exception that has occurred
	 * @throws IOException
	 */
	private void buildErrorResponse(HttpServletResponse httpResponse, Exception ex) throws IOException {
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = httpResponse.getWriter();
		writer.print(ex.getMessage());
		writer.flush();
		writer.close();
	}
}