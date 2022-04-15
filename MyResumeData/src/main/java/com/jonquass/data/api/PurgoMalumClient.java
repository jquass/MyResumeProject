package com.jonquass.data.api;

import com.jonquass.core.algebra.Result;
import com.jonquass.core.algebra.Results;
import com.jonquass.core.api.error.PurgoMalumError;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Singleton
public record PurgoMalumClient(HttpClient httpClient) {

    private static final System.Logger LOG = System.getLogger(PurgoMalumClient.class.getName());

    private static final String PATH_TEMPLATE = "https://www.purgomalum.com/service/plain?text=%s";

    @Inject
    public PurgoMalumClient {
    }

    public Result<String, PurgoMalumError> censorText(String text) {
        String encodedString;
        try {
            encodedString = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(PurgoMalumError.ERROR_ENCODING_TEXT);
        }

        URI uri;
        try {
            uri = new URI(String.format(PATH_TEMPLATE, encodedString));
        } catch (URISyntaxException e) {
            LOG.log(System.Logger.Level.ERROR, e);
            return Results.error(PurgoMalumError.INVALID_URI);
        }

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(uri)
                                         .GET()
                                         .build();
        String response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                    .thenApply(HttpResponse::body)
                                    .join();
        LOG.log(System.Logger.Level.INFO, response);
        return Results.value(response);
    }
}
