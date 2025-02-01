package com.incode.simpleservice.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FreeServiceCompanyDTO(
    String cin,
    String name,
    String registrationDate,
    String address,
    Boolean isActive
) {
}
