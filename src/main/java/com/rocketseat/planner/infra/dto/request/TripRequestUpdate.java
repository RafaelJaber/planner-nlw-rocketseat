package com.rocketseat.planner.infra.dto.request;

public record TripRequestUpdate(String destination,
                                String starts_at,
                                String ends_at,
                                String owner_email,
                                String owner_name,
                                Boolean is_confirmed) {
}
