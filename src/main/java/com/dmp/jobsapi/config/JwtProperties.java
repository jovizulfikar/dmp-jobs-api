package com.dmp.jobsapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@Configuration("JwtProperties")
public class JwtProperties {
    private Long expire;
    private String secret;
}
