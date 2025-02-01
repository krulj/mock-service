package com.incode.simpleservice.api.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

  private final int code;
  public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public ResourceNotFoundException(ServiceErrorCodes error) {
    super(error.getDescription());
    code = error.getCode();
  }

  public int getCode() {
    return code;
  }

}
