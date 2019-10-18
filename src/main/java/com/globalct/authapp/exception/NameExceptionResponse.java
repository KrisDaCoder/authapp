package com.globalct.authapp.exception;

public class NameExceptionResponse {

  private String name;

  public NameExceptionResponse() {
  }

  public NameExceptionResponse(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
