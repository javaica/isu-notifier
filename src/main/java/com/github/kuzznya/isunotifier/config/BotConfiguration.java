package com.github.kuzznya.isunotifier.config;

import com.github.kuzznya.isunotifier.model.BotProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BotProperties.class)
public class BotConfiguration {

    @Bean
    public TelegramBot telegramBot(BotProperties properties) {
        return TelegramBotAdapter.build(properties.getApiKey());
    }
}
