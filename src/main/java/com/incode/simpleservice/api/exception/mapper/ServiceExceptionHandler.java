package com.incode.simpleservice.api.exception.mapper;

import com.incode.simpleservice.api.exception.BadRequestException;
import com.incode.simpleservice.api.exception.ResourceNotFoundException;
import com.incode.simpleservice.api.exception.ServiceErrorCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

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

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers,
                                                                        HttpStatusCode status, WebRequest request) {
    return ResponseEntity
        .status(BadRequestException.STATUS)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ExceptionResponse(ServiceErrorCodes.MISSING_REQUEST_PARAM.getCode(), ex.getMessage()));
  }

}
