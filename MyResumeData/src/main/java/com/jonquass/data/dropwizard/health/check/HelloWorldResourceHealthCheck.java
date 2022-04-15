package com.jonquass.data.dropwizard.health.check;

import com.codahale.metrics.health.HealthCheck;
import com.jonquass.data.dropwizard.api.HelloWorldClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HelloWorldResourceHealthCheck extends HealthCheck {

    private final HelloWorldClient helloWorldClient;

    @Inject
    HelloWorldResourceHealthCheck(HelloWorldClient helloWorldClient) {
        this.helloWorldClient = helloWorldClient;
    }

    @Override
    protected Result check() {
        return helloWorldClient.getGreeting()
                        .either(
                                greeting -> Result.healthy(greeting.message()),
                                greetingErrorCode -> Result.unhealthy(greetingErrorCode.toString())
                        );
    }
}
