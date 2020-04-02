package com.botplatform;

import com.botplatform.util.http.HttpWrapper;
import com.botplatform.util.json.JsonWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;

class BotFactory {

    private String tokenEnv;
    private String botUrl;
    private String botName;

    private HttpWrapper httpWrapper;
    private JsonWrapper jsonWrapper;

    private BotCredentials botCredentials;
    private BotController botController;


    static class FactoryBuilder {

        private String tokenEnv;
        private String botUrl;
        private String botName;

        public FactoryBuilder() {
        }

        public static FactoryBuilder newBuilder() {
            return new FactoryBuilder();
        }

        public FactoryBuilder setTokenEnv(String tokenEnv) {
            this.tokenEnv = tokenEnv;
            return this;
        }

        public FactoryBuilder setBotUrl(String botUrl) {
            this.botUrl = botUrl;
            return this;
        }

        public FactoryBuilder setBotName(String botName) {
            this.botName = botName;
            return this;
        }

        BotFactory build() {
            final var botFactory = new BotFactory();
            botFactory.tokenEnv = this.tokenEnv;
            botFactory.botUrl = this.botUrl;
            botFactory.botName = this.botName;

            /*
             * Utility classes
             */
            var objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            botFactory.jsonWrapper = new JsonWrapper(objectMapper);
            botFactory.httpWrapper = new HttpWrapper(HttpClient.newBuilder().build());

            /*
             * Platform core classes
             */
            botFactory.botCredentials = new BotCredentials(System.getenv(tokenEnv));
            botFactory.botController = new BotController(botFactory.botCredentials,
                    botFactory.httpWrapper,
                    botFactory.jsonWrapper);

            return botFactory;
        }
    }


    private BotFactory() {
    }


    String getBotName() {
        return botName;
    }

    BotController getController() {
        return botController;
    }

    JsonWrapper getJsonWrapper() {
        return jsonWrapper;
    }
}
