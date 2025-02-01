package com.incode.simpleservice.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public record CompanyMatchDTO(
    String info,
    CompanyDTO match,
    List<CompanyDTO> otherResults
) {

  public CompanyMatchDTO(String message) {
    this(message, null, null);
  }

  public CompanyMatchDTO(CompanyDTO match, List<CompanyDTO> otherResults) {
    this(null, match, otherResults);
  }
}
