package com.jonquass.core.dropwizard.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(builder = ImmutableGreetingResponse.Builder.class)
@Value.Immutable
public abstract class GreetingResponse {
    public abstract long count();

    public abstract String message();

    public abstract boolean censored();
}
