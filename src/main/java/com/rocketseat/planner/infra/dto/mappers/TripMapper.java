package com.rocketseat.planner.infra.dto.mappers;


import com.rocketseat.planner.infra.dto.request.TripRequestPayload;
import com.rocketseat.planner.domain.entities.Trip;
import com.rocketseat.planner.infra.dto.response.TripResponseBasic;
import com.rocketseat.planner.infra.dto.response.TripResponseDetailed;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TripMapper {

    public Trip toEntity(TripRequestPayload requestPayload) {
        return Trip.builder()
                .destination(requestPayload.destination())
                .startsAt(LocalDateTime.parse(requestPayload.starts_at(), DateTimeFormatter.ISO_DATE_TIME))
                .endsAt(LocalDateTime.parse(requestPayload.ends_at(), DateTimeFormatter.ISO_DATE_TIME))
                .isConfirmed(false)
                .ownerName(requestPayload.owner_name())
                .ownerEmail(requestPayload.owner_email())
                .build();
    }

    public TripResponseBasic toResponseBasic(Trip trip) {
        return new TripResponseBasic(
                trip.getId(),
                trip.getDestination(),
                trip.getStartsAt(),
                trip.getEndsAt(),
                trip.getIsConfirmed()
        );
    }

    public TripResponseDetailed toResponseDetailed(Trip trip) {
        return new TripResponseDetailed(
                trip.getId(),
                trip.getDestination(),
                trip.getStartsAt(),
                trip.getEndsAt(),
                trip.getIsConfirmed(),
                trip.getOwnerName(),
                trip.getOwnerEmail()
        );
    }
}
