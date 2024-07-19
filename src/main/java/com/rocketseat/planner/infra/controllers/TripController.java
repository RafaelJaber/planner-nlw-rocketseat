package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.domain.entities.Activity;
import com.rocketseat.planner.domain.entities.Link;
import com.rocketseat.planner.domain.entities.Participant;
import com.rocketseat.planner.domain.services.ActivityService;
import com.rocketseat.planner.domain.services.LinkService;
import com.rocketseat.planner.infra.dto.mappers.ActivityMapper;
import com.rocketseat.planner.infra.dto.mappers.LinkMapper;
import com.rocketseat.planner.infra.dto.mappers.ParticipantMapper;
import com.rocketseat.planner.infra.dto.mappers.TripMapper;
import com.rocketseat.planner.infra.dto.request.*;
import com.rocketseat.planner.infra.dto.response.*;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.services.ParticipantService;
import com.rocketseat.planner.domain.services.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final ParticipantService participantService;
    private final ActivityService activityService;
    private final LinkService linkService;
    private final TripMapper tripMapper;
    private final ParticipantMapper participantMapper;
    private final ActivityMapper activityMapper;
    private final LinkMapper linkMapper;

    public TripController(
            TripMapper tripMapper,
            TripService tripService,
            ParticipantService participantService,
            ActivityService activityService,
            LinkService linkService,
            ParticipantMapper participantMapper,
            ActivityMapper activityMapper,
            LinkMapper linkMapper
    ) {
        this.tripMapper = tripMapper;
        this.tripService = tripService;
        this.participantService = participantService;
        this.activityService = activityService;
        this.linkService = linkService;
        this.participantMapper = participantMapper;
        this.activityMapper = activityMapper;
        this.linkMapper = linkMapper;
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

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<ParticipantResponseOfTrip> getTripParticipants(@PathVariable UUID tripId) {
        Trip trip = tripService.findById(tripId);
        List<Participant> participants = participantService.findByTripId(trip.getId());

        TripResponseDetailed responseTrip = tripMapper.toResponseDetailed(trip);
        List<ParticipantResponseDetailed> responseParticipants = participants
                .stream()
                .map(participantMapper::toResponseDetailed)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ParticipantResponseOfTrip(responseTrip, responseParticipants));
    }

    @GetMapping("/{tripId}/activities")
    public ResponseEntity<ActivitiesResponseOfTrip> getTripActivities(@PathVariable UUID tripId) {
        Trip trip = tripService.findById(tripId);
        List<Activity> activities = activityService.findByTripId(trip.getId());

        TripResponseDetailed responseTrip = tripMapper.toResponseDetailed(trip);
        List<ActivityResponseDetailed> responseActivities = activities
                .stream()
                .map(activityMapper::toResponseDetailed)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ActivitiesResponseOfTrip(responseTrip, responseActivities));
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

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload requestPayload) {
        Trip trip = tripMapper.toEntity(requestPayload);
        Trip createdTrip = tripService.create(trip);
        participantService.registerParticipantsToTrip(requestPayload.emails_to_invite(), createdTrip.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(createdTrip.getId()));
    }

    @PostMapping("/{tripId}/activities")
    public ResponseEntity<Void> createTripActivity(@PathVariable UUID tripId,
                                                   @RequestBody ActivityRequestPayload payload) {
        Trip trip = tripService.findById(tripId);
        activityService.create(payload, trip);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{tripId}/links")
    public ResponseEntity<Void> createTripLink(@PathVariable UUID tripId,
                                               @RequestBody LinkRequestPayload payload) {
        Trip trip = tripService.findById(tripId);
        linkService.create(payload, trip);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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

    @PatchMapping("/{tripId}/invite-new-participants")
    public ResponseEntity<Void> inviteNewParticipants(@PathVariable UUID tripId,
                                                      @RequestBody ParticipantRequestInvite participantRequestInvite) {
        participantService.registerParticipantsToTrip(participantRequestInvite.emails(), tripId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
