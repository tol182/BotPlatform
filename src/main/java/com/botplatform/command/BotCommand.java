package com.botplatform.command;

public enum BotCommand {

    START("/start"),

    HELP("/help"),

    SETTINGS("/settings");

    private String command;

    BotCommand(String command) {
        this.command = command;
    }

    public String command() {
        return command;
    }
}
