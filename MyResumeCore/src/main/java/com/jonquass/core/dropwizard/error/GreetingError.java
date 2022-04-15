package com.jonquass.core.dropwizard.error;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GreetingError {
    INVALID_URI,
    ERROR_PARSING_RESPONSE,
    NO_API_RESPONSE,
    ;

    @JsonCreator
    public String toString() {
        return name();
    }
}
