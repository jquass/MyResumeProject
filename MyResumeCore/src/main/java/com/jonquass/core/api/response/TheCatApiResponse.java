package com.jonquass.core.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

@JsonDeserialize(builder = ImmutableTheCatApiResponse.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
public abstract class TheCatApiResponse {

    public abstract String url();

    public abstract int width();

    public abstract int height();

    public abstract String id();
}
