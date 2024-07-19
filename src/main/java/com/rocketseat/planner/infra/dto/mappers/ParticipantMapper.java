package com.rocketseat.planner.infra.dto.mappers;

import com.rocketseat.planner.domain.entities.Participant;
import com.rocketseat.planner.infra.dto.response.ParticipantResponseDetailed;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantResponseDetailed toResponseDetailed(Participant participant) {
        return new ParticipantResponseDetailed(
                participant.getId(),
                participant.getName(),
                participant.getEmail(),
                participant.getIsConfirmed()
        );
    }
}
