package com.jonquass.data.creator;

import com.jonquass.core.algebra.Result;
import com.jonquass.core.dropwizard.response.GreetingResponse;
import com.jonquass.core.api.error.PurgoMalumError;
import com.jonquass.core.dropwizard.response.ImmutableGreetingResponse;
import com.jonquass.data.api.PurgoMalumClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public record GreetingCreator(PurgoMalumClient purgoMalumClient) {
    private static final System.Logger LOG = System.getLogger(GreetingCreator.class.getName());

    @Inject
    public GreetingCreator {
    }

    private static final String GREETING_TEMPLATE = "Hello, %s!";
    private static final String DEFAULT_NAME = "Stranger";

    public GreetingResponse createGreeting(long count) {
        return ImmutableGreetingResponse.builder()
                                        .count(count)
                                        .message(formatMessage(DEFAULT_NAME))
                                        .censored(false)
                                        .build();
    }

    public GreetingResponse createGreeting(long count, String name) {
        String truncatedName = name.substring(0, Math.min(name.length(), 20));
        Result<String, PurgoMalumError> result = purgoMalumClient.censorText(truncatedName);
        return result.either(
                censoredName -> ImmutableGreetingResponse.builder()
                                                         .count(count)
                                                         .message(formatMessage(censoredName))
                                                         .censored(!truncatedName.equals(censoredName))
                                                         .build(),
                error -> {
                    LOG.log(System.Logger.Level.ERROR, "Error calling Purgo Malum Client %s", error);
                    return ImmutableGreetingResponse.builder()
                                                    .count(count)
                                                    .message(formatMessage(truncatedName))
                                                    .censored(false)
                                                    .build();
                }
        );
    }

    private static String formatMessage(String name) {
        return String.format(GREETING_TEMPLATE, name);
    }
}
