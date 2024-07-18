package com.rocketseat.planner.domain.errors;


public class NotFoundResourceException extends RuntimeException {
    public NotFoundResourceException(String value) {
        super(String.format("Resource with value: %s, not found.", value));
    }
}
