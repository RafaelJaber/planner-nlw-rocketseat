package com.rocketseat.planner.domain.services;

import com.rocketseat.planner.domain.entities.Participant;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.domain.errors.NotFoundResourceException;
import com.rocketseat.planner.domain.errors.ParticipantAlreadyConfirmedException;
import com.rocketseat.planner.domain.repository.ParticipantRepository;
import com.rocketseat.planner.infra.dto.request.ParticipantRequestConfirm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final TripService tripService;

    public ParticipantService(ParticipantRepository participantRepository, TripService tripService) {
        this.participantRepository = participantRepository;
        this.tripService = tripService;
    }

    public Participant findById(UUID id) {
        return participantRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(id.toString())
        );
    }

    public void registerParticipantsToTrip(List<String> participantsToInvite, UUID tripId) {
        Trip trip = tripService.findById(tripId);
        List<Participant> participants = participantsToInvite
                .stream()
                .map(email -> new Participant(email, trip))
                .toList();

        participantRepository.saveAll(participants);
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {

    }

    public void confirmParticipantToTrip(UUID participantId, ParticipantRequestConfirm participantData) {
        Participant participant = findById(participantId);

        if (participant.getIsConfirmed()) {
            throw new ParticipantAlreadyConfirmedException(participantId.toString());
        }

        participant.setIsConfirmed(true);
        participant.setName(participantData.name());

        participantRepository.save(participant);
    }
}
