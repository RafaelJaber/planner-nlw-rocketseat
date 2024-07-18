package com.rocketseat.planner.infra.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record TripResponseBasic(UUID id,
                                String destination,
                                LocalDateTime startsAt,
                                LocalDateTime endsAt,
                                Boolean isConfirmed) {
}
