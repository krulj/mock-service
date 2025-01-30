package com.incode.simpleservice.third_party.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record PremiumServiceCompaniesDTO(
    String companyIdentificationNumber,
    String companyName,
    String registrationDate,
    String companyFullAddress,
    Boolean isActive
) {
}
