package com.incode.simpleservice.api.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

  private final int code;
  public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public BadRequestException(ServiceErrorCodes error) {
    super(error.getDescription());
    code = error.getCode();
  }

  public int getCode() {
    return code;
  }

}
