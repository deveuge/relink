package com.deveuge.relink.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deveuge.relink.api.config.RedisConfiguration;
import com.deveuge.relink.api.enumeration.ApiOperation;
import com.deveuge.relink.api.model.dto.LinkRequestDTO;
import com.deveuge.relink.api.model.entity.Link;
import com.deveuge.relink.api.model.entity.User;
import com.deveuge.relink.api.model.repository.LinkRepository;
import com.deveuge.relink.api.model.service.KGSService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ApiController {

	@Autowired
	private KGSService kgsService;

	@Autowired
	private LinkRepository linkRepository;

	/**
	 * Creates a new shortened URL. If the original URL is already shortened,
	 * returns the original generated hash.
	 * 
	 * @param auth        {@link Authentication} Authenticated principal
	 * @param linkRequest {@link LinkRequestDTO} Link request creation data
	 * @return {@link String} Shortened hash
	 */
	@Operation(summary = "Creates a new shortened URL")
	@PostMapping("/create")
	public String create(Authentication auth, @RequestBody LinkRequestDTO linkRequest) {
		User user = (User) auth.getPrincipal();

		Optional<Link> existingLink = linkRepository.findByOriginalURL(linkRequest.getOriginalURL());
		if (existingLink.isPresent()) {
			return existingLink.get().getHash();
		}

		String hash = kgsService.getHash();
		Link link = Link.builder().hash(hash).originalURL(linkRequest.getOriginalURL()).user(user)
				.expirationDate(linkRequest.getExpirationDate()).build();
		linkRepository.insert(link);

		return hash;
	}

	/**
	 * Gets the original URL of a shortened link
	 * 
	 * @param hash {@link String} Link hash
	 * @return {@link String} Original URL
	 */
	@Operation(summary = "Gets the original URL of a shortened link")
	@GetMapping("/get/{hash}")
	@Cacheable(value = RedisConfiguration.CACHE_NAME, key = "#hash", unless = "#result == null")
	public String get(@Parameter(description = "Link hash") @PathVariable String hash) {
		Link link = linkRepository.findById(hash).orElse(null);
		if (link == null) {
			log.error("{} Request to non-existent URL '{}'", ApiOperation.GET, hash);
			return null;
		}
		if (link.isExpired()) {
			log.error("Request to expired URL '{}'. Deleting link", link.getHash());
			linkRepository.delete(link);
			return null;
		}

		return link.getOriginalURL();
	}

	/**
	 * Deletes a shortened URL
	 * 
	 * @param auth {@link Authentication} Authenticated principal
	 * @param hash {@link String} Link hash
	 * @return boolean true if deleted, false otherwise
	 */
	@Operation(summary = "Deletes a shortened URL")
	@DeleteMapping("/delete/{hash}")
	@CacheEvict(value = RedisConfiguration.CACHE_NAME, key = "#hash")
	public boolean delete(Authentication auth, @Parameter(description = "Link hash") @PathVariable String hash) {
		User user = (User) auth.getPrincipal();
		Link link = linkRepository.findById(hash).orElse(null);
		if (link == null) {
			log.error("{} Request to non-existent URL '{}'", ApiOperation.DELETE, hash);
			return false;
		}
		if (!link.isOwner(user)) {
			log.error("{} Request per non-creator user '{}' of URL '{}'", ApiOperation.DELETE, user.getApiKey(), hash);
			return false;
		}
		linkRepository.delete(link);
		return true;
	}

}
