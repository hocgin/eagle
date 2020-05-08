package in.hocg.eagle.basic.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 * 缓存配置
 *
 * @author hocgin
 */
@Configuration
public class CacheConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    @ConditionalOnMissingBean(RedisCacheManagerBuilderCustomizer.class)
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> {
            builder.cacheDefaults(redisCacheConfiguration());
            CacheKeys.getCACHE_KEY_MAP()
                .forEach((cacheName, amount) -> builder.withCacheConfiguration(cacheName, redisCacheConfiguration().entryTtl(Duration.ofSeconds(amount))));
        };

    }

    /**
     * 缓存默认配置
     *
     * @return
     */
    private RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
            .entryTtl(Duration.ofHours(1L))
            .computePrefixWith(cacheName -> appName + ":" + cacheName + ":")
            .disableCachingNullValues();
    }
}
