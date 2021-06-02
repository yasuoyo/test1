/*
 *    Copyright (c) 2018-2025, diditech All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: diditech
 */

package com.example.test.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author diditech
 * @date 2018/8/16
 * RestTemplate
 */
@Configuration
public class RedisConfig {


	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();

		//使用fastjson序列化
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		// value值的序列化采用fastJsonRedisSerializer
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		// key的序列化采用StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());

		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	public JedisPool jedisPool(@Value("${spring.redis.host}") String hostName,
                               @Value("${spring.redis.port}") int port,
                               @Value("${spring.redis.password}") String password,
                               @Value("${spring.redis.pool.max-idle}") int maxIdle,
                               @Value("${spring.redis.pool.min-idle}") int minIdle,
                               @Value("${spring.redis.timeout}") int maxWaitMillis){

		JedisPoolConfig poolCofig = this.poolCofig(maxIdle, minIdle, maxWaitMillis);
		return new JedisPool(poolCofig, hostName, port, maxWaitMillis, password);
	}

	public JedisPoolConfig poolCofig(int maxIdle, int minIdle, long maxWaitMillis) {
		JedisPoolConfig poolCofig = new JedisPoolConfig();
		poolCofig.setMaxIdle(maxIdle);
		poolCofig.setMinIdle(minIdle);
		poolCofig.setMaxWaitMillis(maxWaitMillis);
		return poolCofig;
	}
}
