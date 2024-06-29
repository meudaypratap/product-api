package com.demo.product.api.controller;

import com.demo.product.api.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("Method argument not valid ", e);
    FieldError field = e.getFieldError();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body((field == null
            ? e.getMessage()
            : (field.getField() + " " + field.getDefaultMessage())));
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<String> handleAllException(Exception ex) {
    log.error("Unexpected Exception: ", ex);
    return ResponseEntity.internalServerError().body(ex.getMessage());
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public final ResponseEntity<Object> handleProductNotException(ProductNotFoundException ex) {
    log.error("Product not found: ", ex);
    return ResponseEntity.notFound().build();
  }
}
