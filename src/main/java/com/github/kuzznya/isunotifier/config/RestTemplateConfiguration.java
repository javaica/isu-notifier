package com.github.kuzznya.isunotifier.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .defaultHeader("cookie", "ORA_WWV_RAC_INSTANCE=2; REMEMBER_ME=C852F72EFA68834B8AC386A19E9A95AE:BAAE493A1B8EFD75D6F2797A37492EA8; CUSTOM_COOKIE=17.01.2020 00:44:53; ISU_AP_COOKIE=ORA_WWV-1weo/FyJdjUAEvRoNPx6zuNO; ISU_LIB_SID=ORA_WWV-1weo/FyJdjUAEvRoNPx6zuNO")
                .build();
    }
}
