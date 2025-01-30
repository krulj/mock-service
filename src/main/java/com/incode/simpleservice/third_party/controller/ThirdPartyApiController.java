package com.incode.simpleservice.third_party.controller;

import com.incode.simpleservice.third_party.dto.FreeServiceCompaniesDTO;
import com.incode.simpleservice.third_party.dto.PremiumServiceCompaniesDTO;
import com.incode.simpleservice.third_party.service.ThirdPartyApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/third-party")
public class ThirdPartyApiController {

  private ThirdPartyApiService service;

  public ThirdPartyApiController(ThirdPartyApiService service) {
    this.service = service;
  }

  @GetMapping("/free-third-party")
  public ResponseEntity<List<FreeServiceCompaniesDTO>> queryCompaniesFreeApi(@RequestParam(name = "query") String query) {
    if (Math.random() < 0.4) {
      // Simulate service not available at Controller level
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
    }
    List<FreeServiceCompaniesDTO> result = service.queryCompaniesFreeApi(query);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/premium-third-party")
  public ResponseEntity<List<PremiumServiceCompaniesDTO>> queryCompaniesPremiumApi(@RequestParam(name = "query") String query) {
    if (Math.random() < 0.1) {
      // Simulate service not available at Controller level
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
    }
    List<PremiumServiceCompaniesDTO> result = service.queryCompaniesPremiumApi(query);
    return ResponseEntity.ok(result);
  }
}
