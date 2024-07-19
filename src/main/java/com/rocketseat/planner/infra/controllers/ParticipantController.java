package com.rocketseat.planner.infra.controllers;

import com.rocketseat.planner.domain.services.ParticipantService;
import com.rocketseat.planner.infra.dto.request.ParticipantRequestConfirm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/{participantId}/confirm")
    public ResponseEntity<Void> confirmParticipant(@PathVariable UUID participantId,
                                                   @RequestBody ParticipantRequestConfirm participantRequest) {
        participantService.confirmParticipantToTrip(participantId, participantRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
