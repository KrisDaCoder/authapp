package com.globalct.authapp.security;

public class SecurityConstants {

  public static final String SIGN_UP_URLS = "/api/auth/**";
  public static final String H2_URL = "/h2-console/**";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String[] AUTH_WHITELIST = {
          "/swagger-resources/**",
          "/swagger-ui.html",
          "/v2/api-docs",
          "/webjars/**"
  };

}
