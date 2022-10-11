package com.sportynote.server.util;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    private RedisTemplate<String, Object> redisTemplate;
    private RedisTemplate<String, Object> redisBlackListTemplate;
    RedisUtil(RedisTemplate<String, Object> redisTemplate, RedisTemplate<String, Object> redisBlackListTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisBlackListTemplate = redisBlackListTemplate;
    }
    public void set(String key, Object o, long minutes) {
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        this.redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return this.redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public void setBlackList(String key, Object o, int minutes) {
        this.redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        this.redisBlackListTemplate.opsForValue().set(key, o, (long)minutes, TimeUnit.MINUTES);
    }

    public Object getBlackList(String key) {
        return this.redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return this.redisBlackListTemplate.delete(key);
    }
    public boolean hasKeyWhiteList(String key) {
        return this.redisTemplate.hasKey(key);
    }
}
