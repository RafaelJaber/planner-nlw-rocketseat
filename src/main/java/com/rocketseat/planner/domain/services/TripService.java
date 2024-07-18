package com.rocketseat.planner.domain.services;

import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.errors.NotFoundResourceException;
import com.rocketseat.planner.domain.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findById(UUID id) {
        return tripRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(id.toString())
        );
    }

    public Trip create(Trip trip) {
        return tripRepository.save(trip);
    }
}
