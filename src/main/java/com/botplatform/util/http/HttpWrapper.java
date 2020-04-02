package com.botplatform.util.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HttpWrapper {

    private final HttpClient client;

    public HttpWrapper(HttpClient client) {
        this.client = client;
    }

    public ResponseWrapper get(String url) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url)).build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Unexpected error when sending request to {}\n{}", url, e.getMessage());
            return new ResponseWrapper(500, "Unexpected error");
        }

        return new ResponseWrapper(response.statusCode(), response.body());
    }

    public boolean isSuccessful(ResponseWrapper response) {
        return response.getStatusCode() == 200
                || response.getStatusCode() == 201
                || response.getStatusCode() == 202;
    }
}
