package com.rocketseat.planner.domain.errors;

public class ParticipantAlreadyConfirmedException extends RuntimeException {
    public ParticipantAlreadyConfirmedException(String tripId) {
        super(String.format("Participant with id: '%s' is already confirmed", tripId));
    }
}
