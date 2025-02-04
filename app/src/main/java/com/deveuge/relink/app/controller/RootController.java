package com.deveuge.relink.app.controller;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.deveuge.relink.app.model.dto.LinkRequestDTO;
import com.deveuge.relink.app.model.service.APIService;

import jakarta.servlet.http.HttpServletResponse;

@Controller("/")
public class RootController {

	@Value("${relink.base.url}")
	private String baseURL;

	@Autowired
	private APIService apiService;

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("{hash}")
	public String redirectShortenedLink(@PathVariable String hash, ModelMap model,
			HttpServletResponse httpServletResponse) {
		try {
			String originalURL = apiService.getUrl(hash);
			if (originalURL == null) {
				return viewError(model, "The shortened url does not exist");
			}
			httpServletResponse.setHeader("Location", originalURL);
			httpServletResponse.setStatus(302);
		} catch (RestClientException ex) {
			return viewError(model, "The service is currently unavailable. Please try again later");
		}
		return null;
	}

	@PostMapping
	public String generateLink(ModelMap model, @RequestParam String url) {
		UrlValidator urlValidator = new UrlValidator(new String[] { "http", "https" });
		if (!urlValidator.isValid(url)) {
			return viewPartialError(model, "Please provide a valid URL");
		}

		try {
			LinkRequestDTO request = new LinkRequestDTO(url);
			String hash = apiService.createUrl(request);
			if (hash == null) {
				return viewPartialError(model,
						"An error has occurred while processing your application. Please try again later");
			}
			model.addAttribute("error", false);
			model.addAttribute("link", composeUrl(hash));
		} catch (RestClientException ex) {
			return viewPartialError(model, "The service is currently unavailable. Please try again later");
		}

		return "fragments/result :: result";
	}

	private String viewError(ModelMap model, String message) {
		addErrorToModel(model, message);
		return "index";
	}

	private String viewPartialError(ModelMap model, String message) {
		addErrorToModel(model, message);
		return "fragments/result :: result";
	}

	private void addErrorToModel(ModelMap model, String message) {
		model.addAttribute("error", true);
		model.addAttribute("message", message);

	}

	private String composeUrl(String hash) {
		return String.format("%s/%s", baseURL, hash);
	}
}
