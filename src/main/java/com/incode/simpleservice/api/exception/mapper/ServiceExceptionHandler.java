package com.incode.simpleservice.api.exception.mapper;

import com.incode.simpleservice.api.exception.BadRequestException;
import com.incode.simpleservice.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {


  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
    return ResponseEntity
        .status(ResourceNotFoundException.STATUS)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ExceptionResponse(ex.getCode(), ex.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
    return ResponseEntity
        .status(BadRequestException.STATUS)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ExceptionResponse(ex.getCode(), ex.getMessage()));
  }


}
