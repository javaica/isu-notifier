package com.github.kuzznya.isunotifier.service;

import com.github.kuzznya.isunotifier.entity.User;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class TelegramUpdatesHandler implements UpdatesListener {

    private final UserService userService;
    private final TelegramBot bot;

    public TelegramUpdatesHandler(TelegramBot bot, UserService userService) {
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void registerSelf() {
        bot.setUpdatesListener(this);
    }

    public void handleUpdate(Update update) {
        Message message = update.message();

        switch (message.text()) {
            case "/start":
                bot.execute(new SendMessage(message.chat().id(),  "You can /subscribe or /unsubscribe"));
                break;

            case "/subscribe":
                User user = new User();
                user.setId(message.chat().id());
                user.setUsername(message.from().username());

                userService.add(user);

                bot.execute(new SendMessage(user.getId(),  user.getUsername() + " successfully added to notification list"));

                break;

            case "/unsubscribe":
                userService.remove(userService.findUserById(message.chat().id()));
                bot.execute(new SendMessage(message.chat().id(), "User removed from notification list"));

                break;

            default:
                bot.execute(new SendMessage(message.chat().id(), "Did not understand, sorry"));
        }
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(this::handleUpdate);
        return CONFIRMED_UPDATES_ALL;
    }

    @PreDestroy
    public void deregisterSelf() {
        bot.removeGetUpdatesListener();
    }
}
