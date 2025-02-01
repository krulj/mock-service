package com.incode.simpleservice.api.exception;

public enum ServiceErrorCodes {

  // Third party api
  THIRD_PARTY_API_UNAVAILABLE(10000, "Third party API unavailable."),
  THIRD_PARTY_NO_RESULT(10001, "No result for provided query."),

  // Verification
  VERIFICATION_NOT_FOUND(20000, "Verification with provided id not found"),
  VERIFICATION_NON_UNIQUE(20001, "Verification with provided id is not unique"),

  //General
  MISSING_REQUEST_PARAM(30000, "Missing request parameter"),
  INVALID_UUID_REQUEST_PARAM(30001, "Invalid request parameter. Value should be UUID."),
  INTERNAL_SERVICE_ERROR(30002, "Internal server error");

  private final String description;
  private final int code;

  ServiceErrorCodes(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return this.description + " (" + code + ")";
  }
}
