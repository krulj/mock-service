package com.incode.simpleservice.api.controller;

import com.incode.simpleservice.api.dto.VerificationDTO;
import com.incode.simpleservice.api.service.VerificationService;
import com.incode.simpleservice.api.validation.ParameterValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/verification/")
@Tag(name="Verification controller", description = "Getting info about verifications")
public class VerificationController {

  private final VerificationService verificationService;

  public VerificationController(VerificationService verificationService) {
    this.verificationService = verificationService;
  }

  @GetMapping("/{verification-id}")
  @Operation(summary = "Return verification by UUID")
  public ResponseEntity<VerificationDTO> getByVerificationId(@PathVariable("verification-id") String id) {
    ParameterValidator.validateUUID(id);
    VerificationDTO result = verificationService.getByVerificationId(id);
    return ResponseEntity.ok(result);
  }

}
