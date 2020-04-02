package com.botplatform;

import com.botplatform.command.BaseHelpCommandHandler;
import com.botplatform.command.BaseSettingsCommandHandler;
import com.botplatform.command.BaseStartCommandHandler;
import com.botplatform.command.BotCommand;
import com.botplatform.command.CommandHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BotPlatform {

    private final String botTokenEnv;
    // TODO: add mechanism to obtain bot real name from TG
    private final String botName;
    private final String botUrl;

    private BotContext context;
    private BotController controller;

    public BotPlatform(String botTokenEnv, String botName) {
        this(botTokenEnv, botName, null);
    }

    public BotPlatform(String botTokenEnv, String botName, String botUrl) {
        this.botTokenEnv = botTokenEnv;
        this.botName = botName;
        this.botUrl = botUrl;
    }

    public void initialize() {
        var factory = BotFactory.FactoryBuilder
                .newBuilder()
                .setTokenEnv(botTokenEnv)
                .setBotName(botName)
                .setBotUrl(botUrl)
                .build();

        context = BotContext.getBotContext();
        context.setBotFactory(factory);
        controller = factory.getController();

        registerDefaultHandlers();
    }

    public void subscribe() {
        if (botUrl != null) {
            var success = controller.setWebhook(botUrl);

            if (success) log.info("Webhook set to " + botUrl);
        }
    }

    public void handleEvent(String event) {
        controller.handleEvent(event);
    }

    public void registerHandler(String command, CommandHandler handler) {
        controller.registerHandler(command, handler);
    }


    private void registerDefaultHandlers() {
        controller.registerHandler(BotCommand.START, new BaseStartCommandHandler());
        controller.registerHandler(BotCommand.HELP, new BaseHelpCommandHandler());
        controller.registerHandler(BotCommand.SETTINGS, new BaseSettingsCommandHandler());
    }
}
