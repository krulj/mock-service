package com.incode.simpleservice.api.manipulator;

import com.incode.simpleservice.api.dto.VerificationDTO;
import com.incode.simpleservice.api.repository.entity.Verification;

import java.time.format.DateTimeFormatter;

public class VerificationManipulator {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private VerificationManipulator() {
    // Utility
  }

  public static VerificationDTO toDto(Verification verification) {
    return new VerificationDTO(
        verification.getVerificationId(),
        verification.getQuery(),
        verification.getTimestamp().format(formatter),
        verification.getResult(),
        verification.getSource()
    );
  }

}

