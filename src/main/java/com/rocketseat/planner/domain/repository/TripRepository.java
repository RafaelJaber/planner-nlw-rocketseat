package com.rocketseat.planner.domain.repository;

import com.rocketseat.planner.domain.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
