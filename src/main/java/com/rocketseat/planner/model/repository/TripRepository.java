package com.rocketseat.planner.model.repository;

import com.rocketseat.planner.model.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
