package com.github.kuzznya.isunotifier.service;

import com.github.kuzznya.isunotifier.model.IsuProperties;
import com.github.kuzznya.isunotifier.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

@Service
public class IsuService {

    private final Logger logger = LoggerFactory.getLogger(IsuService.class);

    private final RestTemplate restTemplate;

    private final NotificationService notificationService;

    private State state = State.PREPARE;

    private final IsuProperties properties;

    private final Pattern preparingPagePattern;

    public IsuService(RestTemplate restTemplate, NotificationService notificationService, IsuProperties properties) {
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
        this.preparingPagePattern = Pattern.compile("<div class=\"panel-body\">[.\\s\\n]*" +
                "Cтраница учебного плана находится в подготовке открытия выбора\\. Попробуйте перейти к учебному плану позже\\.[.\\n\\s]*" +
                "</div>");
        this.properties = properties;
    }

    @Scheduled(fixedDelay = 30000)
    public void checkISU() {
        State newState;

        ResponseEntity<String> response;

        try {
            response = restTemplate.getForEntity(properties.getUri(), String.class);
        } catch (RestClientException ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
