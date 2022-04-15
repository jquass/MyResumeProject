package com.jonquass.data.dropwizard.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonquass.core.algebra.Result;
import com.jonquass.core.algebra.Results;
import com.jonquass.core.dropwizard.response.GreetingResponse;
import com.jonquass.core.dropwizard.error.GreetingError;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public record HelloWorldClient(HttpClient httpClient, ObjectMapper objectMapper) {

    private static final System.Logger LOG = System.getLogger(HelloWorldClient.class.getName());
    private static final String PATH = "http://localhost:9000/hello-world";

    @Inject
    public HelloWorldClient {
    }

    public Result<GreetingResponse, GreetingError> getGreeting() {
        URI uri;
        try {
            uri = new URI(PATH);
        } catch (URISyntaxException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(GreetingError.INVALID_URI);
        }

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(uri)
                                         .GET()
                                         .build();
        String response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                    .thenApply(HttpResponse::body)
                                    .join();
        GreetingResponse greetingResponse;
        try {
            greetingResponse = objectMapper.readValue(response, GreetingResponse.class);
        } catch (JsonProcessingException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(GreetingError.ERROR_PARSING_RESPONSE);
        }

        if (greetingResponse == null) {
            LOG.log(System.Logger.Level.ERROR, "No response from " + PATH);
            return Results.error(GreetingError.NO_API_RESPONSE);
        }

        LOG.log(System.Logger.Level.INFO, response);
        return Results.value(greetingResponse);
    }
}
