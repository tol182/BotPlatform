package com.botplatform;

/**
 * Application-aware context.
 */
public class BotContext {

    private static BotContext botContext;

    private BotFactory botFactory;

    public static BotContext getBotContext() {
        if (botContext == null) {
            botContext = new BotContext();
        }

        return botContext;
    }

    void setBotFactory(BotFactory botFactory) {
        this.botFactory = botFactory;
    }


    public String getBotName() {
        return botFactory.getBotName();
    }
}
