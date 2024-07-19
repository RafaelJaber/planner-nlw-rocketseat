package com.rocketseat.planner.infra.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityResponseDetailed(UUID id,
                                       String title,
                                       LocalDateTime occursAt) {
}
