package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.domain.entities.Activity;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.services.ActivityService;
import com.rocketseat.planner.domain.services.TripService;
import com.rocketseat.planner.infra.dto.mappers.ActivityMapper;
import com.rocketseat.planner.infra.dto.mappers.TripMapper;
import com.rocketseat.planner.infra.dto.request.ActivityRequestPayload;
import com.rocketseat.planner.infra.dto.response.ActivitiesResponseOfTrip;
import com.rocketseat.planner.infra.dto.response.ActivityResponseDetailed;
import com.rocketseat.planner.infra.dto.response.TripResponseDetailed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripActivityController {

    private final TripService tripService;
    private final ActivityService activityService;
    private final TripMapper tripMapper;
    private final ActivityMapper activityMapper;

    public TripActivityController(
            TripService tripService,
            ActivityService activityService,
            TripMapper tripMapper,
            ActivityMapper activityMapper
    ) {
        this.tripService = tripService;
        this.activityService = activityService;
        this.tripMapper = tripMapper;
        this.activityMapper = activityMapper;
    }


    @GetMapping("/{tripId}/activities")
    public ResponseEntity<ActivitiesResponseOfTrip> getTripActivities(@PathVariable UUID tripId) {
        Trip trip = tripService.findById(tripId);
        List<Activity> activities = activityService.findByTripId(trip.getId());

        TripResponseDetailed responseTrip = tripMapper.toResponseDetailed(trip);
        List<ActivityResponseDetailed> responseActivities = activities
                .stream()
                .map(activityMapper::toResponseDetailed)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ActivitiesResponseOfTrip(responseTrip, responseActivities));
    }

    @PostMapping("/{tripId}/activities")
    public ResponseEntity<Void> createTripActivity(@PathVariable UUID tripId,
                                                   @RequestBody ActivityRequestPayload payload) {
        Trip trip = tripService.findById(tripId);
        activityService.create(payload, trip);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
