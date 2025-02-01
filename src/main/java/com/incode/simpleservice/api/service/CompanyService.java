package com.incode.simpleservice.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incode.simpleservice.api.configuration.CompanyServiceProperties;
import com.incode.simpleservice.api.dto.CompanyDTO;
import com.incode.simpleservice.api.dto.CompanyMatchDTO;
import com.incode.simpleservice.api.dto.CompanyQueryDTO;
import com.incode.simpleservice.api.exception.ServiceErrorCodes;
import com.incode.simpleservice.api.manipulator.ThirdPartyApiManipulator;
import com.incode.simpleservice.api.repository.entity.Verification;
import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties(CompanyServiceProperties.class)
public class CompanyService {

  private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
  private static final ObjectMapper mapper = new ObjectMapper();

  private static final String QUERY = "query=";

  private final RestTemplate restTemplate;
  private final CompanyServiceProperties properties;
  private final VerificationService verificationService;

  public CompanyService(CompanyServiceProperties properties, RestTemplate restTemplate, VerificationService verificationService) {
    this.restTemplate = restTemplate;
    this.properties = properties;
    this.verificationService = verificationService;
  }

  public CompanyQueryDTO queryCompaniesByIdentification(@RequestParam(name = "query") String query,
                                                        @RequestParam(name = "verificationId") String verificationId) {

    List<CompanyDTO> companyDTOs = new ArrayList<>();
    String source = null;

    boolean freeServiceUp = true;
    try {
      List<CompanyDTO> freeCompanies = getFreeActiveCompanies(query);
      companyDTOs.addAll(freeCompanies);
      source = properties.getFreeUrl();
    } catch (ServiceUnavailable e) {
      freeServiceUp = false;
      logger.warn("Free third party service unavailable");
    }

    boolean premiumServiceUp = true;
    if (!freeServiceUp || companyDTOs.isEmpty()) {
      try {
        List<CompanyDTO> premiumCompanies = getPremiumActiveCompanies(query);
        companyDTOs.addAll(premiumCompanies);
        source = properties.getFreeUrl();
      } catch (ServiceUnavailable e) {
        premiumServiceUp = false;
        logger.warn("Premium third party service unavailable");
      }
    }

    // Create response objects
    CompanyMatchDTO match = createCompanyMatchDTO(freeServiceUp, premiumServiceUp, companyDTOs);
    CompanyQueryDTO result = new CompanyQueryDTO(verificationId, query, match);

    // Insert into verifications table
    Verification verification = createVerificationEntity(query, verificationId, result, source);
    verificationService.saveVerificationData(verification);

    return result;
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

  private CompanyMatchDTO createCompanyMatchDTO(boolean freeServiceUp, boolean premiumServiceUp, List<CompanyDTO> companyDTOs) {
    if (!freeServiceUp && !premiumServiceUp) {
      return new CompanyMatchDTO(ServiceErrorCodes.THIRD_PARTY_API_UNAVAILABLE.getMessage());
    } else if (companyDTOs.isEmpty()) {
      return new CompanyMatchDTO(ServiceErrorCodes.THIRD_PARTY_NO_RESULT.getMessage());
    } else {
      List<CompanyDTO> otherResults = companyDTOs.size() > 1 ? companyDTOs.subList(1, companyDTOs.size()) : null;
      return new CompanyMatchDTO(companyDTOs.get(0), otherResults);
    }
  }

  private Verification createVerificationEntity(String verificationId, String query, CompanyQueryDTO result, String source) {
    String resultJson = null;
    try {
      resultJson = mapper.writeValueAsString(result);
    } catch (JsonProcessingException e) {
      logger.warn("Result can't be converted to json");
    }
    Verification verification = Verification.newBuilder()
        .verificationId(verificationId)
        .query(query)
        .timestamp(LocalDateTime.now())
        .result(resultJson)
        .source(source)
        .build();
    return verification;
  }
}
