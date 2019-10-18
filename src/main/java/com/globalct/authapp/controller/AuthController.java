package com.globalct.authapp.controller;

import com.globalct.authapp.dto.UserDto;
import com.globalct.authapp.model.User;
import com.globalct.authapp.payload.JwtLoginSuccessResponse;
import com.globalct.authapp.payload.LoginRequest;
import com.globalct.authapp.security.JwtTokenProvider;
import com.globalct.authapp.service.implementation.MapValidationErrorService;
import com.globalct.authapp.service.UserService;
import com.globalct.authapp.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.globalct.authapp.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private UserService userService;
  private MapValidationErrorService mapValidationErrorService;
  private UserValidator userValidator;
  private JwtTokenProvider tokenProvider;
  private AuthenticationManager authenticationManager;

  public AuthController(UserService userService, MapValidationErrorService mapValidationErrorService, UserValidator userValidator, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.mapValidationErrorService = mapValidationErrorService;
    this.userValidator = userValidator;
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
    ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
    if (errorMap != null)
      return errorMap;

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getName(),
                    loginRequest.getPassword()
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult bindingResult) {
    userValidator.validate(user, bindingResult);

    ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
    if (errorMap != null)
      return errorMap;

    UserDto newUserDto = userService.saveUser(user);

    return new ResponseEntity<UserDto>(newUserDto, HttpStatus.OK);
  }

}
