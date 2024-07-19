package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.domain.entities.Participant;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.services.ParticipantService;
import com.rocketseat.planner.domain.services.TripService;
import com.rocketseat.planner.infra.dto.mappers.ParticipantMapper;
import com.rocketseat.planner.infra.dto.mappers.TripMapper;
import com.rocketseat.planner.infra.dto.request.ParticipantRequestInvite;
import com.rocketseat.planner.infra.dto.request.TripRequestPayload;
import com.rocketseat.planner.infra.dto.response.ParticipantResponseDetailed;
import com.rocketseat.planner.infra.dto.response.ParticipantResponseOfTrip;
import com.rocketseat.planner.infra.dto.response.TripCreateResponse;
import com.rocketseat.planner.infra.dto.response.TripResponseDetailed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripParticipantController {

    private final TripService tripService;
    private final ParticipantService participantService;
    private final TripMapper tripMapper;
    private final ParticipantMapper participantMapper;

    public TripParticipantController(
            TripService tripService,
            ParticipantService participantService,
            TripMapper tripMapper,
            ParticipantMapper participantMapper
    ) {
        this.tripService = tripService;
        this.participantService = participantService;
        this.tripMapper = tripMapper;
        this.participantMapper = participantMapper;
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

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload requestPayload) {
        Trip trip = tripMapper.toEntity(requestPayload);
        Trip createdTrip = tripService.create(trip);
        participantService.registerParticipantsToTrip(requestPayload.emails_to_invite(), createdTrip.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(createdTrip.getId()));
    }

    @PatchMapping("/{tripId}/invite-new-participants")
    public ResponseEntity<Void> inviteNewParticipants(@PathVariable UUID tripId,
                                                      @RequestBody ParticipantRequestInvite participantRequestInvite) {
        participantService.registerParticipantsToTrip(participantRequestInvite.emails(), tripId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
