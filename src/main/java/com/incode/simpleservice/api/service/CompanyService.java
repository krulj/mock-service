package com.incode.simpleservice.api.service;

import com.incode.simpleservice.api.configuration.CompanyServiceProperties;
import com.incode.simpleservice.api.dto.CompanyDTO;
import com.incode.simpleservice.api.dto.CompanyMatchDTO;
import com.incode.simpleservice.api.dto.CompanyQueryDTO;
import com.incode.simpleservice.api.exception.ServiceErrorCodes;
import com.incode.simpleservice.api.manipulator.ThirdPartyApiManipulator;
import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties(CompanyServiceProperties.class)
public class CompanyService {

  private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
  private static final String QUERY = "query=";

  private final RestTemplate restTemplate;
  private final CompanyServiceProperties properties;

  public CompanyService(CompanyServiceProperties properties, RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.properties = properties;
  }

  public CompanyQueryDTO queryCompaniesByIdentification(@RequestParam(name = "query") String query,
                                                        @RequestParam(name = "verificationId") String verificationId) {

    List<CompanyDTO> result = new ArrayList<>();

    boolean freeServiceUp = true;
    try {
      List<CompanyDTO> freeCompanies = getFreeActiveCompanies(query);
      result.addAll(freeCompanies);
    } catch (ServiceUnavailable e) {
      freeServiceUp = false;
      logger.warn("Free service unavailable");
    }

    boolean premiumServiceUp = true;
    if (!freeServiceUp || result.isEmpty()) {
      try {
        List<CompanyDTO> premiumCompanies = getPremiumActiveCompanies(query);
        result.addAll(premiumCompanies);
      } catch (ServiceUnavailable e) {
        premiumServiceUp = false;
        logger.warn("Premium service unavailable");
      }
    }

    CompanyMatchDTO match;
    if (!freeServiceUp && !premiumServiceUp) {
      match = new CompanyMatchDTO(ServiceErrorCodes.THIRD_PARTY_API_UNAVAILABLE.getFullError(), null, null);
    } else if (result.isEmpty()) {
      match = new CompanyMatchDTO("No result", null, null);
    } else {
      List<CompanyDTO> otherResults = result.size() > 1 ? result.subList(1, result.size()) : null;
      match = new CompanyMatchDTO(null, result.get(0), otherResults);
    }

    return new CompanyQueryDTO(verificationId, query, match);
  }

  private List<CompanyDTO> getFreeActiveCompanies(String query) {
    List<CompanyDTO> result = new ArrayList<>();

    String freeUrl = properties.getFreeUrl() + "?" + QUERY + query;
    ResponseEntity<FreeServiceCompaniesDTO[]> response = restTemplate.getForEntity(freeUrl, FreeServiceCompaniesDTO[].class);

    if (response.hasBody() && response.getBody().length > 0) {
      List<CompanyDTO> companies = ThirdPartyApiManipulator.fromFreeApi(response.getBody());
      List<CompanyDTO> active = companies.stream().filter(CompanyDTO::isActive).toList();
      result.addAll(active);
    }
    return result;
  }

  private List<CompanyDTO> getPremiumActiveCompanies(String query) {
    List<CompanyDTO> result = new ArrayList<>();

    String freeUrl = properties.getPremiumUrl() + "?" + QUERY + query;
    ResponseEntity<PremiumServiceCompaniesDTO[]> response = restTemplate.getForEntity(freeUrl, PremiumServiceCompaniesDTO[].class);

    if (response.hasBody() && response.getBody().length > 0) {
      List<CompanyDTO> companies = ThirdPartyApiManipulator.fromPremiumApi(response.getBody());
      List<CompanyDTO> active = companies.stream().filter(CompanyDTO::isActive).toList();
      result.addAll(active);
    }
    return result;
  }
}
