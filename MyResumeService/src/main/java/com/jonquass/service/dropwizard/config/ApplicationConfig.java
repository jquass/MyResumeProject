package com.jonquass.service.dropwizard.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;

@Immutable
public class ApplicationConfig extends Configuration {
    @NotEmpty
    private String accessControlAllowOrigin;

    @JsonProperty
    public String getAccessControlAllowOrigin() {
        return accessControlAllowOrigin;
    }

    @JsonProperty
    public void setDefaultAccessControlAllowOrigin(String defaultAccessControlAllowOrigin) {
        this.accessControlAllowOrigin = defaultAccessControlAllowOrigin;
    }
}
