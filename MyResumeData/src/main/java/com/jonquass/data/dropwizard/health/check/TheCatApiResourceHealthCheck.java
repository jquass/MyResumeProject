package com.jonquass.data.dropwizard.health.check;

import com.codahale.metrics.health.HealthCheck;
import com.jonquass.data.dropwizard.api.HelloWorldClient;
import com.jonquass.data.dropwizard.api.TheCatApiClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TheCatApiResourceHealthCheck extends HealthCheck {

    private final TheCatApiClient theCatApiClient;

    @Inject
    TheCatApiResourceHealthCheck(TheCatApiClient theCatApiClient) {
        this.theCatApiClient = theCatApiClient;
    }

    @Override
    protected Result check() {
        return theCatApiClient.getCat()
                              .either(
                                      cat -> Result.healthy(cat.url()),
                                      catError -> Result.unhealthy(catError.toString())
                              );
    }
}
