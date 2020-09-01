package com.github.kuzznya.isunotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IsuNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsuNotifierApplication.class, args);
    }

}
