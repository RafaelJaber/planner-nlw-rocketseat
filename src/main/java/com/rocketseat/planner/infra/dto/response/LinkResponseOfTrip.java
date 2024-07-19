package com.rocketseat.planner.infra.dto.response;

import java.util.List;

public record LinkResponseOfTrip(TripResponseDetailed trip,
                                 List<LinkResponseDetailed> links) {
}
