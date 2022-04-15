package com.jonquass.data.dropwizard.health.check;

import com.codahale.metrics.health.HealthCheck;
import com.jonquass.data.api.TheCatApiClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TheCatApiClientHealthCheck extends HealthCheck {

    private final TheCatApiClient theCatApiClient;

    @Inject
    TheCatApiClientHealthCheck(TheCatApiClient theCatApiClient) {
        this.theCatApiClient = theCatApiClient;
    }

    @Override
    protected Result check() {
        return theCatApiClient.fetchCat()
                              .either(
                                      result -> Result.healthy(result.url()),
                                      errorCode -> Result.unhealthy(errorCode.toString())
                              );
    }
}
