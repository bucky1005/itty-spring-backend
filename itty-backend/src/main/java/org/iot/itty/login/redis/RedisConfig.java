package org.iot.itty.login.redis;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {

		// Redis 설정 정보를 LettuceConnectionFactory에 담아서 반환
		return new LettuceConnectionFactory(host, port);
	}

	// Redis 작업을 수행하기 위해 RedisTemplate 객체를 생성하여 반환
	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		/* accessToken 만료 시 refreshToken을 받아와 발급하기 위해 redisTemplate 사용 */
		/* redisTemplate을 통해 set, get, delete 사용 가능 */
		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();

		/* redis-cli를 통해 조회 시 알아볼 수 있는 형태로 포맷팅 */
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
			.fromConnectionFactory(redisConnectionFactory());

		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
			.prefixCacheNameWith("cache:") // Key의 접두사로 "cache:"를 앞에 붙여 저장
			.entryTtl(Duration.ofMinutes(30)); // 캐시 수명(유효기간)을 30분으로 설정
		builder.cacheDefaults(configuration);

		return builder.build();
	}
}
