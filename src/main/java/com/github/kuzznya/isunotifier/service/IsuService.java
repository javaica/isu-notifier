package com.github.kuzznya.isunotifier.service;

import com.github.kuzznya.isunotifier.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.regex.Pattern;

@Service
public class IsuService {

    private final Logger logger = LoggerFactory.getLogger(IsuService.class);

    private final RestTemplate restTemplate;

    private final NotificationService notificationService;

    private State state = State.PREPARE;

    private final URI uri = URI.create("https://isu.ifmo.ru/pls/apex/f?p=2437:22:111331497525840::NO::P22_LIST:1");

    private final Pattern preparingPagePattern;

    public IsuService(RestTemplate restTemplate, NotificationService notificationService) {
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
        this.preparingPagePattern = Pattern.compile("<div class=\"panel-body\">[.\\s\\n]*" +
                "Данная страница находится в подготовке открытия выбора\\.[.\\n\\s]*" +
                "</div>");
    }

    @Scheduled(fixedDelay = 30000)
    public void checkISU() {
        State newState;

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody())
            newState = State.FALLEN;
        else if (
                preparingPagePattern.matcher(response.getBody()).find()
        )
            newState = State.PREPARE;
        else
            newState = State.OK;

        if (newState == state)
            return;

        logger.info("ISU state is now " + state.name());
        
        notificationService.notifyAll(newState);

        state = newState;
    }
}
