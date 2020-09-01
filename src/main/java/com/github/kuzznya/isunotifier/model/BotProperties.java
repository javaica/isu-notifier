package com.github.kuzznya.isunotifier.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bot")
@Data
public class BotProperties {
    private String apiKey;
}
