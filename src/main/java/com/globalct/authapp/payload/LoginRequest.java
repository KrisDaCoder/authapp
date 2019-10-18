package com.globalct.authapp.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

  @NotBlank(message = "Username cannot be blank")
  private String name;
  @NotBlank(message = "Password cannot be blank")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
