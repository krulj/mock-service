package com.incode.simpleservice.api.exception.mapper;

public record ExceptionResponse(
    int code,
    String message
) {
}
