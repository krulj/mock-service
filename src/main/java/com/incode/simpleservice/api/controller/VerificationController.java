package com.incode.simpleservice.api.controller;

import com.incode.simpleservice.api.dto.VerificationDTO;
import com.incode.simpleservice.api.service.VerificationService;
import com.incode.simpleservice.api.validation.ParameterValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/verification/")
public class VerificationController {

  private final VerificationService verificationService;

  public VerificationController(VerificationService verificationService) {
    this.verificationService = verificationService;
  }

  @GetMapping("/{verification-id}")
  public ResponseEntity<VerificationDTO> getByVerificationId(@PathVariable("verification-id") String id) {
    ParameterValidator.validateUUID(id);
    VerificationDTO result = verificationService.getByVerificationId(id);
    return ResponseEntity.ok(result);
  }

}
