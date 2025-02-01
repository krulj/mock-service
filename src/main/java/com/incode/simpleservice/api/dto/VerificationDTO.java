package com.incode.simpleservice.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record VerificationDTO(
    String verificationId,
    String queryText,
    String timestamp,
    String result,
    String source
) {
}
