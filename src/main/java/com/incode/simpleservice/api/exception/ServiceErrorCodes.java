package com.incode.simpleservice.api.exception;

public enum ServiceErrorCodes {

  THIRD_PARTY_API_UNAVAILABLE(10000, "Third party api unavailable.");

  private final String description;
  private final int code;

  ServiceErrorCodes(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getFullError() {
    return "#" + this.code + ": " + this.description;
  }


}
