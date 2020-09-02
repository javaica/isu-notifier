package com.github.kuzznya.isunotifier.service;

import com.github.kuzznya.isunotifier.entity.User;
import com.github.kuzznya.isunotifier.model.State;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final TelegramBot bot;

    private final UserService userService;

    public NotificationService(TelegramBot bot, UserService userService) {
        this.bot = bot;
        this.userService = userService;
    }

    public void notify(State state, User user) {
        switch (state) {
            case OK:
                bot.execute(new SendMessage(user.getId(), "WARNING ISU state is now OK"));
                break;
            case FALLEN:
                bot.execute(new SendMessage(user.getId(), "ISU state is now ERROR"));
                break;
            case PREPARE:
                bot.execute(new SendMessage(user.getId(), "ISU state is now PREPARING"));
                break;
        }
    }

    public void notifyAll(State state) {
        userService.getAll().forEach(user -> notify(state, user));
    }
}
