package com.botplatform;

public interface BotApi {

    String BASE = "https://api.telegram.org/bot";

    String WEBHOOK = "setWebhook?url=%s";

    String MESSAGES_SEND_MESSAGE = "sendMessage?text=%s&chat_id=%s";
}
