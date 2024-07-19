package com.rocketseat.planner.infra.dto.response;

import java.util.UUID;

public record LinkResponseDetailed(UUID id,
                                   String title,
                                   String url) {
}
