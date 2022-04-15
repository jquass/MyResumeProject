package com.jonquass.core.api.error;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TheCatApiError {
    INVALID_CAT_API_URI,
    MISSING_API_KEY,
    NO_API_RESPONSE,
    ERROR_PARSING_RESPONSE,
    ;

    @JsonCreator
    public String toString() {
        return name();
    }
}
