package com.incode.simpleservice.third_party.service;

import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.repository.ThirdPartyFreeDao;
import com.incode.simpleservice.third_party.repository.ThirdPartyPremiumDao;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;
import com.incode.simpleservice.third_party.manipulator.CompanyManipulator;
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
    List<FreeServiceCompaniesDTO> result = CompanyManipulator.toFreeDtos(thirdPartyFreeDao.queryByIdentification(query));
    return result;
  }

  public List<PremiumServiceCompaniesDTO> queryCompaniesPremiumApi(String query) {
    List<PremiumServiceCompaniesDTO> result = CompanyManipulator.toPremiumDtos(thirdPartyPremiumDao.queryByIdentification(query));
    return result;
  }

}
