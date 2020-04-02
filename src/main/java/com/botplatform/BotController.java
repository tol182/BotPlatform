package com.botplatform;

import com.botplatform.command.BotCommand;
import com.botplatform.command.CommandHandler;
import com.botplatform.util.http.HttpWrapper;
import com.botplatform.util.json.JsonWrapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BotController {

    private final BotCredentials credentials;
    private final HttpWrapper http;
    private final JsonWrapper json;

    private Map<String, CommandHandler> handlers;

    public BotController(BotCredentials credentials,
                         HttpWrapper http,
                         JsonWrapper json) {
        this.credentials = credentials;
        this.http = http;
        this.json = json;

        this.handlers = new HashMap<>();
    }

    public boolean setWebhook(String botUrl) {
        var url = BotApi.BASE
                + credentials.getToken()
                + "/"
                + String.format(BotApi.WEBHOOK, botUrl);

        var response = http.get(url);
        return http.isSuccessful(response);
    }

    public void registerHandler(BotCommand command, CommandHandler handler) {
        handlers.put(command.command(), handler);
    }

    public void registerHandler(String command, CommandHandler handler) {
        handlers.put(command, handler);
    }

    public void handleEvent(String event) {
        var chatId = json.getProperty(event, "message.chat.id");
        var command = json.getProperty(event, "message.text");

        handleCommand(chatId, command);
    }

    public void handleCommand(String chatId, BotCommand command) {
        this.handleCommand(chatId, command.command());
    }

    public void handleCommand(String chatId, String command) {
        var handler = handlers.get(command);

        if (handler == null) {
            sendMessage(chatId, "Command " + command + " is not supported by this bot.");
            return;
        }

        var response = handler.handleCommand();
        sendMessage(chatId, response);
    }

    public void sendMessage(String chatId, String message) {
        var encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        var url = BotApi.BASE
                + credentials.getToken()
                + "/"
                + String.format(BotApi.MESSAGES_SEND_MESSAGE, encodedMessage, chatId);

        http.get(url);
    }
}
