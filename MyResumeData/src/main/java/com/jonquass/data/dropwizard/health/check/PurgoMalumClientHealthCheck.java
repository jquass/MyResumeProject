package com.jonquass.data.dropwizard.health.check;

import com.codahale.metrics.health.HealthCheck;
import com.jonquass.data.api.PurgoMalumClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PurgoMalumClientHealthCheck extends HealthCheck {

    private static final String CENSOR_TEST_STRING = "Test bellend";
    private final PurgoMalumClient purgoMalumClient;

    @Inject
    PurgoMalumClientHealthCheck(PurgoMalumClient purgoMalumClient) {
        this.purgoMalumClient = purgoMalumClient;
    }

    @Override
    protected Result check() {
        return purgoMalumClient.censorText(CENSOR_TEST_STRING)
                               .either(Result::healthy, errorCode -> Result.unhealthy(errorCode.toString()));
    }
}
