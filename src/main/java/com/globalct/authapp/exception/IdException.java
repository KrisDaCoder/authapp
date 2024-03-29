package com.globalct.authapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdException extends RuntimeException {

  public IdException(String message) {
    super(message);
  }

}
