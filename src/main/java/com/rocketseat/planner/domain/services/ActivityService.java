package com.rocketseat.planner.domain.services;

import com.rocketseat.planner.domain.entities.Activity;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.repository.ActivityRepository;
import com.rocketseat.planner.infra.dto.request.ActivityRequestPayload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findByTripId(UUID tripId) {
        return activityRepository.findByTripId(tripId);
    }

    public Activity create(ActivityRequestPayload payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);
        return activityRepository.save(newActivity);
    }
}
