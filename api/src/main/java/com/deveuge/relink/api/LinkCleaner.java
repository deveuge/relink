package com.deveuge.relink.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.deveuge.relink.api.config.RedisConfiguration;
import com.deveuge.relink.api.model.entity.Link;
import com.deveuge.relink.api.model.repository.LinkRepository;
import com.deveuge.relink.api.model.service.KGSService;

@Component
public class LinkCleaner {

	@Autowired
	private LinkRepository linkRepository;
	@Autowired
	private KGSService kgsService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void execute() {
		List<Link> expiredLinks = linkRepository.findExpired();
		List<String> hashLinks = expiredLinks.stream().map(Link::getHash).collect(Collectors.toList());
		List<String> cacheKeyLinks = hashLinks.stream()
				.map(hash -> RedisConfiguration.CACHE_NAME + CacheKeyPrefix.SEPARATOR + hash)
				.collect(Collectors.toList());

		redisTemplate.delete(cacheKeyLinks);
		kgsService.deleteHashes(hashLinks);
		linkRepository.deleteAll(expiredLinks);
	}
}
