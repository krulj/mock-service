package com.incode.simpleservice.api.service.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incode.simpleservice.api.dto.CompanyQueryDTO;
import com.incode.simpleservice.api.dto.VerificationDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@SpringBootTest
@Testcontainers
class VerificationControllerIT {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
  private RestTemplate restTemplate = new RestTemplate();
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  public static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  public static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  public static void configure(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);

  }

  @Test
  void getVerificationData_success() throws JsonProcessingException {
    // Given
    String uuid = UUID.randomUUID().toString();
    String query = "a";
    ResponseEntity<CompanyQueryDTO> company = restTemplate.exchange(
        "http://localhost:9876/v1/api/backend-service?query=" + query + "&verificationId=" + uuid,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        new ParameterizedTypeReference<CompanyQueryDTO>() {}
    );

    // When
    ResponseEntity<VerificationDTO> verification = restTemplate.exchange(
        "http://localhost:9876/v1/verification/" + uuid,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        new ParameterizedTypeReference<VerificationDTO>() {}
    );

    String expectedMatch = objectMapper.writeValueAsString(company.getBody());

    // Then
    Assertions.assertNotNull(verification);
    Assertions.assertNotNull(verification.getBody());
    Assertions.assertEquals(query, verification.getBody().queryText());
    Assertions.assertEquals(expectedMatch, verification.getBody().result());
  }

}