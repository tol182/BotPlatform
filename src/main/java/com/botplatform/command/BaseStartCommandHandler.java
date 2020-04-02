package com.botplatform.command;

import com.botplatform.BotContext;

public class BaseStartCommandHandler implements CommandHandler {

    @Override
    public String handleCommand() {
        return "Hi! This is " + BotContext.getBotContext().getBotName() + " bot.\n" +
                "To get the list of available commands, type /help";
    }
}
