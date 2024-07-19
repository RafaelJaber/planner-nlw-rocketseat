package com.rocketseat.planner.infra.dto.response;

import java.util.List;

public record ActivitiesResponseOfTrip(TripResponseDetailed trip,
                                       List<ActivityResponseDetailed> activities) {
}
