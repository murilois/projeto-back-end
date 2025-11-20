package com.stockcontrol.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching // Anotação que ativa o caching
public class CacheConfig {
    // O Spring Boot autoconfigura o RedisCacheManager pq a dependência 'spring-boot-starter-data-redis' está no pom.xml
}