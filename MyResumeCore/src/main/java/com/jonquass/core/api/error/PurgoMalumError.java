package com.jonquass.core.api.error;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PurgoMalumError {
    ERROR_ENCODING_TEXT,
    INVALID_URI
    ;

    @JsonCreator
    public String toString() {
        return name();
    }
}
