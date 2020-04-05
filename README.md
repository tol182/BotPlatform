# Telegram Bot Platform

Lightweight library for writing backend for your Telegram bot.

## Build

```
./gradlew clean build
``` 

## Artifacts

The library consists of a single jar file - `bot-platform.jar`  
It is located under `/build/libs` directory.

## Entry point

The entry point of this library is [BotPlatform.java](src/main/java/com/botplatform/BotPlatform.java) class.

## Usage

Add `bot-platform.jar` to your project, for example to `lib` directory.

With Gradle, you can add local library to dependencies as following:

```
compile files("lib/bot-platform.jar")
```

Once added, you need to create instance of `BotPlatform` class:

```java
    BotPlatform botPlatform = new BotPlatform("BOT_TOKEN_ENV",
        "BOT_FRIENDLY_NAME",
        "BOT_CALLBACK_URL");

    botPlatform.initialize();
    botPlatform.subscribe();
```

Where:
* `BOT_TOKEN_ENV` - is the name of the environment variable that holds access token of your Telegram bot
* `BOT_FRIENDLY_NAME` - is display name of your bot
* `BOT_CALLBACK_URL` - URL of your backend service for Telegram Webhook

After this, your bot is ready to listen incoming commands on `BOT_CALLBACK_URL` and respond to them.  
Currently, you have to provide external service to listen for a POST request on `BOT_CALLBACK_URL`, for example Spring Boot application.  
You should pass request body to `BotPlatform` object:

```java
@RestController
@RequestMapping("/botplatform")
public class BotController {

    @Autowired
    private BotPlatform botPlatform;

    @PostMapping
    public ResponseEntity listenForCommand(@RequestBody String body) {

        botPlatform.handleEvent(body);

        return ResponseEntity.ok("Executed");
    }
}
```

Out of the box, the library provides basic implementation of the `/start`, `/help` and `/settings` commands; you can override them and provide implementations for your commands (indeed, you are encouraged to do so):

```java
    botPlatform.registerHandler("/command", new FancyCommandHandler());
```

Where `FancyCommandHandler` is class that implements `CommandHandler` interface:

```java
public class FancyCommandHandler implements CommandHandler {

    @Override
    public String handleCommand() {
        return "This is the implementation of your bot command";
    }
}
```

## Dependencies

Bot Platform depends on Java 11 runtime.

Internally, library depends on:
* Jackson, for JSON operations
* Log4j 2 for logging
* Lombok for managing boilerplate code

## TODO

* Add more logging output
* Handle invalid parameters input
* REST endpoint to listen for Webhook events
* Support for storing data to persistent backend
* Support for choosing language
* Add unit tests
* Add javadocs
* Add license