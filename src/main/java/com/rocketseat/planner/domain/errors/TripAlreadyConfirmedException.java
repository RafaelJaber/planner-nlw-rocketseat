package com.rocketseat.planner.domain.errors;

public class TripAlreadyConfirmedException extends RuntimeException {
    public TripAlreadyConfirmedException(String tripId) {
        super(String.format("Trip with id: '%s' is already confirmed", tripId));
    }
}
