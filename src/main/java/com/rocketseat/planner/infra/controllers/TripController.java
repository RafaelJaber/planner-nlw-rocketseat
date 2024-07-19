package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.infra.dto.mappers.TripMapper;
import com.rocketseat.planner.infra.dto.request.TripRequestPayload;
import com.rocketseat.planner.infra.dto.request.TripRequestUpdate;
import com.rocketseat.planner.infra.dto.response.TripCreateResponse;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.services.ParticipantService;
import com.rocketseat.planner.domain.services.TripService;
import com.rocketseat.planner.infra.dto.response.TripResponseBasic;
import com.rocketseat.planner.infra.dto.response.TripResponseDetailed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<TripResponseBasic>> findAll() {
        List<Trip> trips = tripService.findAll();

        List<TripResponseBasic> tripResponseBasics = trips
                .stream().map(tripMapper::toResponseBasic).toList();
        return ResponseEntity.status(HttpStatus.OK).body(tripResponseBasics);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponseDetailed> getTripDetails(@PathVariable UUID tripId) {
        Trip trip = tripService.findById(tripId);

        TripResponseDetailed responseDto = tripMapper.toResponseDetailed(trip);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload requestPayload) {
        Trip trip = tripMapper.toEntity(requestPayload);
        Trip createdTrip = tripService.create(trip);
        participantService.registerParticipantsToTrip(requestPayload.emails_to_invite(), createdTrip.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(createdTrip.getId()));
    }

    @PatchMapping("/{tripId}/confirm-trip")
    public ResponseEntity<Void> confirmTrip(@PathVariable UUID tripId) {
        tripService.confirmTrip(tripId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{tripId}")
    public ResponseEntity<TripResponseDetailed> createTrip(@RequestBody TripRequestUpdate requestPayload,
                                                           @PathVariable UUID tripId) {
        Trip trip = tripMapper.toEntity(requestPayload);
        Trip updatedTrip = tripService.updateTrip(tripId, trip);

        TripResponseDetailed responseDto = tripMapper.toResponseDetailed(updatedTrip);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
