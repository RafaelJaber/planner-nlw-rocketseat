package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.domain.entities.Link;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.services.LinkService;
import com.rocketseat.planner.domain.services.TripService;
import com.rocketseat.planner.infra.dto.mappers.LinkMapper;
import com.rocketseat.planner.infra.dto.mappers.TripMapper;
import com.rocketseat.planner.infra.dto.request.LinkRequestPayload;
import com.rocketseat.planner.infra.dto.response.LinkResponseDetailed;
import com.rocketseat.planner.infra.dto.response.LinkResponseOfTrip;
import com.rocketseat.planner.infra.dto.response.TripResponseDetailed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripLinkController {

    private final TripService tripService;
    private final LinkService linkService;
    private final TripMapper tripMapper;
    private final LinkMapper linkMapper;

    public TripLinkController(
            TripService tripService,
            LinkService linkService,
            TripMapper tripMapper,
            LinkMapper linkMapper
    ) {
        this.tripService = tripService;
        this.linkService = linkService;
        this.tripMapper = tripMapper;
        this.linkMapper = linkMapper;
    }


    @GetMapping("/{tripId}/links")
    public ResponseEntity<LinkResponseOfTrip> getTripLinks(@PathVariable UUID tripId) {
        Trip trip = tripService.findById(tripId);
        List<Link> links = linkService.findByTripId(trip.getId());

        TripResponseDetailed responseTrip = tripMapper.toResponseDetailed(trip);
        List<LinkResponseDetailed> responseLinks = links
                .stream()
                .map(linkMapper::toResponseDetailed)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new LinkResponseOfTrip(responseTrip, responseLinks));
    }

    @PostMapping("/{tripId}/links")
    public ResponseEntity<Void> createTripLink(@PathVariable UUID tripId,
                                               @RequestBody LinkRequestPayload payload) {
        Trip trip = tripService.findById(tripId);
        linkService.create(payload, trip);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
