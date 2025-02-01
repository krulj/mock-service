package com.incode.simpleservice.api.manipulator;

import com.incode.simpleservice.api.dto.CompanyDTO;
import com.incode.simpleservice.api.dto.FreeServiceCompanyDTO;
import com.incode.simpleservice.api.dto.PremiumServiceCompanyDTO;

import java.util.Arrays;
import java.util.List;

public class ThirdPartyApiManipulator {

  private ThirdPartyApiManipulator() {
    // Utility
  }

  public static CompanyDTO fromFreeApi(FreeServiceCompanyDTO company) {
    return new CompanyDTO(
        company.cin(),
        company.name(),
        company.registrationDate(),
        company.address(),
        company.isActive()
    );
  }

  public static List<CompanyDTO> fromFreeApi(FreeServiceCompanyDTO[] companies) {
    return Arrays.stream(companies).map(ThirdPartyApiManipulator::fromFreeApi).toList();
  }

  public static CompanyDTO fromPremiumApi(PremiumServiceCompanyDTO company) {
    return new CompanyDTO(
        company.companyIdentificationNumber(),
        company.companyName(),
        company.registrationDate(),
        company.companyFullAddress(),
        company.isActive()
    );
  }

  public static List<CompanyDTO> fromPremiumApi(PremiumServiceCompanyDTO[] companies) {
    return Arrays.stream(companies).map(ThirdPartyApiManipulator::fromPremiumApi).toList();
  }
}

