package com.jonquass.core.dropwizard.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(builder = ImmutableCatResponse.Builder.class)
@Value.Immutable
public abstract class CatResponse {
    public abstract long count();

    public abstract String url();
}
