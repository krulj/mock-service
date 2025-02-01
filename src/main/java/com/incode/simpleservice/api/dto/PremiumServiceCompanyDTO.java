package com.incode.simpleservice.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record PremiumServiceCompanyDTO(
    String companyIdentificationNumber,
    String companyName,
    String registrationDate,
    String companyFullAddress,
    Boolean isActive
) {
}
