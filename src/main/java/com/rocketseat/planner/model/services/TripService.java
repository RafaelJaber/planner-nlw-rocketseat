package com.rocketseat.planner.model.services;

import com.rocketseat.planner.model.entities.Trip;
import com.rocketseat.planner.model.repository.TripRepository;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip create(Trip trip) {
        return tripRepository.save(trip);
    }
}
