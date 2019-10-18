package com.globalct.authapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameException extends RuntimeException {

  public NameException(String message) {
    super(message);
  }
}
