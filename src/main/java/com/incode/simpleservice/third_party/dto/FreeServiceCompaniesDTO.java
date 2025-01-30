package com.incode.simpleservice.third_party.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FreeServiceCompaniesDTO(
    String cin,
    String name,
    String registrationDate,
    String address,
    Boolean isActive
) {
}
