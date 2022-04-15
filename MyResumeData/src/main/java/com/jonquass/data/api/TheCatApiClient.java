package com.jonquass.data.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonquass.core.algebra.Result;
import com.jonquass.core.algebra.Results;
import com.jonquass.core.api.response.TheCatApiResponse;
import com.jonquass.core.api.error.TheCatApiError;
import com.jonquass.data.config.DataConfig;
import io.dropwizard.util.Strings;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public record TheCatApiClient(DataConfig dataConfig, HttpClient httpClient, ObjectMapper objectMapper) {
    private static final System.Logger LOG = System.getLogger(TheCatApiClient.class.getName());
    private static final String API_URL = "https://api.thecatapi.com/v1/images/search?limit=1&size=full";

    @Inject
    public TheCatApiClient {
    }

    public Result<TheCatApiResponse, TheCatApiError> fetchCat() {
        URI uri;
        try {
            uri = new URI(API_URL);
        } catch (URISyntaxException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(TheCatApiError.INVALID_CAT_API_URI);
        }

        String catApiKey = dataConfig.getProperties()
                                     .getProperty("catApiKey");
        if (Strings.isNullOrEmpty(catApiKey)) {
            return Results.error(TheCatApiError.MISSING_API_KEY);
        }

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(uri)
                                         .header("x-api-key", catApiKey)
                                         .GET()
                                         .build();
        String response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                    .thenApply(HttpResponse::body)
                                    .join();
        TheCatApiResponse[] catEgg;
        try {
            catEgg = objectMapper.readValue(response, TheCatApiResponse[].class);
        } catch (JsonProcessingException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(TheCatApiError.ERROR_PARSING_RESPONSE);
        }

        if (catEgg == null) {
            LOG.log(System.Logger.Level.INFO, "No response from TheCatApi");
            return Results.error(TheCatApiError.NO_API_RESPONSE);
        }

        LOG.log(System.Logger.Level.INFO, response);
        return Results.value(catEgg[0]);
    }

}
