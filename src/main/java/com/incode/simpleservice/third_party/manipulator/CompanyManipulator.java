package com.incode.simpleservice.third_party.manipulator;

import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;
import com.incode.simpleservice.third_party.repository.model.FreeServiceCompany;
import com.incode.simpleservice.third_party.repository.model.PremiumServiceCompany;

import java.util.List;

public class CompanyManipulator {

  public static FreeServiceCompaniesDTO toFreeDto(FreeServiceCompany company) {
    return new FreeServiceCompaniesDTO(
        company.getCin(),
        company.getName(),
        company.getRegistrationDate(),
        company.getAddress(),
        company.getIsActive()
    );
  }

  public static List<FreeServiceCompaniesDTO> toFreeDtos(List<FreeServiceCompany> company) {
    return company.stream().map(CompanyManipulator::toFreeDto).toList();
  }

  public static PremiumServiceCompaniesDTO toPremiumDto(PremiumServiceCompany company) {
    return new PremiumServiceCompaniesDTO(
        company.getCompanyIdentificationNumber(),
        company.getCompanyName(),
        company.getRegistrationDate(),
        company.getFullAddress(),
        company.getIsActive()
    );
  }

  public static List<PremiumServiceCompaniesDTO> toPremiumDtos(List<PremiumServiceCompany> company) {
    return company.stream().map(CompanyManipulator::toPremiumDto).toList();
  }
}
