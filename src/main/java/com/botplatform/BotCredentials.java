package com.botplatform;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class BotCredentials {

    private final String token;
}
