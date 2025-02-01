package com.incode.simpleservice.third_party.service;

import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;
import com.incode.simpleservice.third_party.manipulator.CompanyManipulator;
import com.incode.simpleservice.third_party.repository.ThirdPartyFreeDao;
import com.incode.simpleservice.third_party.repository.ThirdPartyPremiumDao;
import com.incode.simpleservice.third_party.repository.model.FreeServiceCompany;
import com.incode.simpleservice.third_party.repository.model.PremiumServiceCompany;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyApiService {

  private ThirdPartyFreeDao thirdPartyFreeDao;
  private ThirdPartyPremiumDao thirdPartyPremiumDao;

  public ThirdPartyApiService(ThirdPartyFreeDao thirdPartyFreeDao, ThirdPartyPremiumDao thirdPartyPremiumDao) {
    this.thirdPartyFreeDao = thirdPartyFreeDao;
    this.thirdPartyPremiumDao = thirdPartyPremiumDao;
  }

  public List<FreeServiceCompaniesDTO> queryCompaniesFreeApi(String query) {
    List<FreeServiceCompany> companies = thirdPartyFreeDao.queryByIdentification(query);
    return CompanyManipulator.toFreeDtos(companies);
  }

  public List<PremiumServiceCompaniesDTO> queryCompaniesPremiumApi(String query) {
    List<PremiumServiceCompany> companies = thirdPartyPremiumDao.queryByIdentification(query);
    return CompanyManipulator.toPremiumDtos(companies);
  }

}
