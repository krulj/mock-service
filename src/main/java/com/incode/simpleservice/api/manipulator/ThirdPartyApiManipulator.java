package com.incode.simpleservice.api.manipulator;

import com.incode.simpleservice.api.dto.CompanyDTO;
import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;

import java.util.Arrays;
import java.util.List;

public class ThirdPartyApiManipulator {

  public static CompanyDTO fromFreeApi(FreeServiceCompaniesDTO company) {
    return new CompanyDTO(
        company.cin(),
        company.name(),
        company.registrationDate(),
        company.address(),
        company.isActive()
    );
  }

  public static List<CompanyDTO> fromFreeApi(FreeServiceCompaniesDTO[] companies) {
    return Arrays.stream(companies).map(ThirdPartyApiManipulator::fromFreeApi).toList();
  }

  public static CompanyDTO fromPremiumApi(PremiumServiceCompaniesDTO company) {
    return new CompanyDTO(
        company.companyIdentificationNumber(),
        company.companyName(),
        company.registrationDate(),
        company.companyFullAddress(),
        company.isActive()
    );
  }

  public static List<CompanyDTO> fromPremiumApi(PremiumServiceCompaniesDTO[] companies) {
    return Arrays.stream(companies).map(ThirdPartyApiManipulator::fromPremiumApi).toList();
  }
}

