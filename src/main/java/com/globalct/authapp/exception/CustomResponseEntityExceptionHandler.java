package com.globalct.authapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = NameException.class)
  public final ResponseEntity<Object> handleNameException(NameException exception, WebRequest webRequest) {
    NameExceptionResponse nameExceptionResponse = new NameExceptionResponse(exception.getMessage());
    return new ResponseEntity<>(nameExceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = IdException.class)
  public final ResponseEntity<Object> handleIdException(IdException exception, WebRequest webRequest) {
    IdExceptionResponse idExceptionResponse = new IdExceptionResponse(exception.getMessage());
    return new ResponseEntity<>(idExceptionResponse, HttpStatus.BAD_REQUEST);
  }

}
