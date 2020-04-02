package com.botplatform.util.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWrapper {

    private final int statusCode;
    private final String body;
}
