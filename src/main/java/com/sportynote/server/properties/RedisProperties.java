package com.sportynote.server.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "redis.server")
@Component
@Getter
@Setter
//@ConstructorBinding
@Configuration
public class RedisProperties {
    private String uri;
    private int port;
}