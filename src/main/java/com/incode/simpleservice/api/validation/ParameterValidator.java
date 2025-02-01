package com.incode.simpleservice.api.validation;

import com.incode.simpleservice.api.exception.BadRequestException;
import com.incode.simpleservice.api.exception.ServiceErrorCodes;

import java.util.UUID;

public class ParameterValidator {

  private ParameterValidator() {
    // Utility
  }

  public static void validateUUID(String uuid) {
    try {
      UUID.fromString(uuid);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException(ServiceErrorCodes.INVALID_UUID_REQUEST_PARAM);
    }

  }
}
