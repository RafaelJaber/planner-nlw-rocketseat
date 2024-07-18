package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.infra.dto.mappers.TripMapper;
import com.rocketseat.planner.infra.dto.request.TripRequestPayload;
import com.rocketseat.planner.model.entities.Trip;
import com.rocketseat.planner.model.services.ParticipantService;
import com.rocketseat.planner.model.services.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripMapper tripMapper;
    private final TripService tripService;
    private final ParticipantService participantService;

    public TripController(
            TripMapper tripMapper,
            TripService tripService,
            ParticipantService participantService
    ) {
        this.tripMapper = tripMapper;
        this.tripService = tripService;
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<String> createTrip(@RequestBody TripRequestPayload requestPayload) {
        Trip trip = tripMapper.toEntity(requestPayload);
        Trip createdTrip = tripService.create(trip);
        participantService.registerParticipantsToTrip(requestPayload.emails_to_invite(), createdTrip.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Trip created");
    }
}
