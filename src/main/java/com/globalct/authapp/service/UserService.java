package com.globalct.authapp.service;

import com.globalct.authapp.dto.UserDto;
import com.globalct.authapp.model.User;

public interface UserService {

  UserDto saveUser(User newUser);

}
