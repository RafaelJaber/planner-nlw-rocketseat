package com.rocketseat.planner.domain.repository;

import com.rocketseat.planner.domain.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {

    public List<Link> findByTripId(UUID tripId);
}
