package com.deveuge.relink.api.config;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration implements CachingConfigurer {

	public static final String CACHE_NAME = "link";

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private String port;

	@Value("${spring.data.redis.username}")
	private String username;

	@Value("${spring.data.redis.password}")
	private String password;

	@Value("${spring.data.redis.timeout}")
	private String timeout;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(Integer.valueOf(port));
		redisStandaloneConfiguration.setUsername(username);
		redisStandaloneConfiguration.setPassword(password);

		JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
		jedisClientConfiguration.connectTimeout(Duration.ofSeconds(Integer.valueOf(timeout)));
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
				jedisClientConfiguration.build());

		return jedisConFactory;
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
		template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
		return template;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig().enableTimeToIdle().entryTtl(Duration.ofMinutes(10))
				.disableCachingNullValues();
	}
}