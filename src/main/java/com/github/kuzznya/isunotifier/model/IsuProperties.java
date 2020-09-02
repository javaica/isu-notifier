package com.github.kuzznya.isunotifier.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "isu")
@Data
public class IsuProperties {
    private String uri;
    private String cookies;
}
