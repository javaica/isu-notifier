package com.github.kuzznya.isunotifier.config;

import com.github.kuzznya.isunotifier.model.IsuProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(IsuProperties.class)
public class IsuConfiguration {

    private final IsuProperties properties;

    public IsuConfiguration(IsuProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .defaultHeader("cookie", properties.getCookies())
                .build();
    }
}
