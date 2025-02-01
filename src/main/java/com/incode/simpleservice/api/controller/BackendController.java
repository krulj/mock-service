package com.incode.simpleservice.api.controller;

import com.incode.simpleservice.api.dto.CompanyQueryDTO;
import com.incode.simpleservice.api.service.CompanyService;
import com.incode.simpleservice.api.validation.ParameterValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/")
@Tag(name="Main API controller", description = "The main API that handles client requests")
public class BackendController {

  private final CompanyService companyService;

  public BackendController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/backend-service")
  @Operation(summary = "Query company by their Identification number")
  public ResponseEntity<CompanyQueryDTO> queryCompaniesByIdentification(@RequestParam(name = "query") String query,
                                                                        @RequestParam(name = "verificationId") String verificationId) {
    ParameterValidator.validateUUID(verificationId);
    CompanyQueryDTO result = companyService.queryCompaniesByIdentification(query, verificationId);
    return ResponseEntity.ok(result);
  }

}
