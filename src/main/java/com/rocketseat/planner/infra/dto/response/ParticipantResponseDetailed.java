package com.rocketseat.planner.infra.dto.response;

import java.util.UUID;

public record ParticipantResponseDetailed(UUID id,
                                          String name,
                                          String email,
                                          Boolean isConfirmed) {
}
