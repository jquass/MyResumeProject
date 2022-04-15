package com.jonquass.data.creator;

import com.jonquass.core.dropwizard.response.CatResponse;
import com.jonquass.core.dropwizard.response.ImmutableCatResponse;
import com.jonquass.data.api.TheCatApiClient;
import com.jonquass.core.api.response.TheCatApiResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public record CatCreator(TheCatApiClient theCatApiClient) {

    private static final String DEFAULT_URL = "https://cdn2.thecatapi.com/images/588.jpg";

    @Inject
    public CatCreator {
    }

    public CatResponse createCat(long count) {
        String url = theCatApiClient.fetchCat()
                                    .either(TheCatApiResponse::url, error -> DEFAULT_URL);
        return ImmutableCatResponse.builder()
                                   .count(count)
                                   .url(url)
                                   .build();
    }
}
