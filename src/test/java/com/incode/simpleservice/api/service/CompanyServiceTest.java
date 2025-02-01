package com.incode.simpleservice.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incode.simpleservice.api.configuration.CompanyServiceProperties;
import com.incode.simpleservice.api.dto.CompanyQueryDTO;
import com.incode.simpleservice.api.dto.FreeServiceCompanyDTO;
import com.incode.simpleservice.api.dto.PremiumServiceCompanyDTO;
import com.incode.simpleservice.api.exception.ServiceErrorCodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

  private static final String VERIFICATION_STRING = "verification1";
  private static final String QUERY_STRING = "query_1";
  private static final String FREE_URL = "http://free.com";
  private static final String PREMIUM_URL = "http://premium.com";

  @Mock
  private CompanyServiceProperties properties;

  @Mock
  private VerificationService verificationService;

  ObjectMapper objectMapper = new ObjectMapper();
  private RestTemplate restTemplate = new RestTemplate();
  private MockRestServiceServer mockServer;

  private CompanyService service;

  @BeforeEach
  public void setUp() {
    mockServer = MockRestServiceServer.createServer(restTemplate);

    Mockito.lenient().when(properties.getFreeUrl()).thenReturn(FREE_URL);
    Mockito.lenient().when(properties.getPremiumUrl()).thenReturn(PREMIUM_URL);

    service = new CompanyService(properties, restTemplate, verificationService);
  }


  @Test
  void testQueryCompaniesByIdentification_freeService_success() throws JsonProcessingException {
    // Given
    FreeServiceCompanyDTO[] companies = {new FreeServiceCompanyDTO("ABCD", "first", null, null, true)};
    mockServer.expect(MockRestRequestMatchers.requestTo(FREE_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators
            .withSuccess(objectMapper.writeValueAsString(companies), MediaType.APPLICATION_JSON));

    // When
    CompanyQueryDTO result = service.queryCompaniesByIdentification(QUERY_STRING, VERIFICATION_STRING);

    // Then
    Assertions.assertNotNull(result);
    Assertions.assertEquals(QUERY_STRING, result.query());
    Assertions.assertNotNull(result.result());
    Assertions.assertNotNull(result.result().match());
    Assertions.assertEquals(companies[0].name(), result.result().match().name());
    Assertions.assertEquals(companies[0].cin(), result.result().match().cin());
  }

  @Test
  void testQueryCompaniesByIdentification_failToPremium_success() throws JsonProcessingException {
    // Given
    mockServer.expect(MockRestRequestMatchers.requestTo(FREE_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators
            .withServiceUnavailable());

    PremiumServiceCompanyDTO[] companies = {new PremiumServiceCompanyDTO("ABCD", "first", null, null, true)};
    mockServer.expect(MockRestRequestMatchers.requestTo(PREMIUM_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators
            .withSuccess(objectMapper.writeValueAsString(companies), MediaType.APPLICATION_JSON));

    // When
    CompanyQueryDTO result = service.queryCompaniesByIdentification(QUERY_STRING, VERIFICATION_STRING);

    // Then
    Assertions.assertNotNull(result);
    Assertions.assertEquals(QUERY_STRING, result.query());
    Assertions.assertNotNull(result.result());
    Assertions.assertNotNull(result.result().match());
    Assertions.assertEquals(companies[0].companyName(), result.result().match().name());
    Assertions.assertEquals(companies[0].companyIdentificationNumber(), result.result().match().cin());
  }

  @Test
  void testQueryCompaniesByIdentification_thirdPartiesUnavailable_emptyResponse() throws JsonProcessingException {
    // Given
    mockServer.expect(MockRestRequestMatchers.requestTo(FREE_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators.withServiceUnavailable());

    mockServer.expect(MockRestRequestMatchers.requestTo(PREMIUM_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators.withServiceUnavailable());

    // When
    CompanyQueryDTO result = service.queryCompaniesByIdentification(QUERY_STRING, VERIFICATION_STRING);

    // Then
    Assertions.assertNotNull(result);
    Assertions.assertEquals(QUERY_STRING, result.query());
    Assertions.assertNotNull(result.result());
    Assertions.assertNotNull(result.result().info());
    Assertions.assertEquals(ServiceErrorCodes.THIRD_PARTY_API_UNAVAILABLE.getMessage(), result.result().info());

  }

  @Test
  void testQueryCompaniesByIdentification_noRecordsForQuery() throws JsonProcessingException {
    // Given
    mockServer.expect(MockRestRequestMatchers.requestTo(FREE_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

    mockServer.expect(MockRestRequestMatchers.requestTo(PREMIUM_URL + "?query=" + QUERY_STRING))
        .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

    // When
    CompanyQueryDTO result = service.queryCompaniesByIdentification(QUERY_STRING, VERIFICATION_STRING);

    // Then
    Assertions.assertNotNull(result);
    Assertions.assertEquals(QUERY_STRING, result.query());
    Assertions.assertNotNull(result.result());
    Assertions.assertNotNull(result.result().info());
    Assertions.assertEquals(ServiceErrorCodes.THIRD_PARTY_NO_RESULT.getMessage(), result.result().info());

  }

}