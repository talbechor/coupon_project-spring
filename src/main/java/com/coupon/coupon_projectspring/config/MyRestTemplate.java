package com.coupon.coupon_projectspring.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class MyRestTemplate {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setReadTimeout(Duration.ofSeconds(6))
                .setConnectTimeout(Duration.ofSeconds(5))
                .build();
    }
}
