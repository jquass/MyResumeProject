package com.jonquass.data.dropwizard.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonquass.core.algebra.Result;
import com.jonquass.core.algebra.Results;
import com.jonquass.core.dropwizard.error.CatError;
import com.jonquass.core.dropwizard.response.CatResponse;
import com.jonquass.core.dropwizard.response.ImmutableCatResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public record TheCatApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
    private static final System.Logger LOG = System.getLogger(TheCatApiClient.class.getName());
    private static final String PATH = "http://localhost:9000/the-cat-api";

    @Inject
    public TheCatApiClient {
    }

    public Result<CatResponse, CatError> getCat() {
        URI uri;
        try {
            uri = new URI(PATH);
        } catch (URISyntaxException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(CatError.INVALID_URI);
        }

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(uri)
                                         .GET()
                                         .build();
        String response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                    .thenApply(HttpResponse::body)
                                    .join();
        CatResponse catResponse;
        try {
            catResponse = objectMapper.readValue(response, ImmutableCatResponse.class);
        } catch (JsonProcessingException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(CatError.ERROR_PARSING_RESPONSE);
        }

        if (catResponse == null) {
            LOG.log(System.Logger.Level.ERROR, "No response from  " + PATH);
            return Results.error(CatError.NO_API_RESPONSE);
        }

        LOG.log(System.Logger.Level.INFO, response);
        return Results.value(catResponse);
    }
}
