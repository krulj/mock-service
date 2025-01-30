package com.incode.simpleservice.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record CompanyQueryDTO(
    String verificationId,
    String query,
    CompanyMatchDTO result
) {
}
