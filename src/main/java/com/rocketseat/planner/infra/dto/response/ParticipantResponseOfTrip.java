package com.rocketseat.planner.infra.dto.response;

import java.util.List;
import java.util.UUID;

public record ParticipantResponseOfTrip(TripResponseDetailed trip,
                                        List<ParticipantResponseDetailed> participants) {
}
