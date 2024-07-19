package com.rocketseat.planner.domain.services;

import com.rocketseat.planner.domain.entities.Activity;
import com.rocketseat.planner.domain.entities.Link;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.repository.LinkRepository;
import com.rocketseat.planner.infra.dto.request.ActivityRequestPayload;
import com.rocketseat.planner.infra.dto.request.LinkRequestPayload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    public List<Link> findByTripId(UUID tripId) {
        return linkRepository.findByTripId(tripId);
    }

    public Link create(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);
        return linkRepository.save(newLink);
    }
}
