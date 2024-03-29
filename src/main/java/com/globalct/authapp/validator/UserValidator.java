package com.globalct.authapp.validator;

import com.globalct.authapp.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return User.class.equals(aClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    User user = (User) object;

    if (user.getPassword().length() < 8) {
      errors.rejectValue("password", "Length", "Password length must be between at least 8");
    }

    if (!user.getPassword().equals(user.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "Match", "Passwords must match");
    }

  }
}
