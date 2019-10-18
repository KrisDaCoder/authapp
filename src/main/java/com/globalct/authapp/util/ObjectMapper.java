package com.globalct.authapp.util;

import com.globalct.authapp.dto.UserDto;
import com.globalct.authapp.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectMapper {

  public UserDto map(User user) {
    UserDto userDto = new UserDto();
    userDto.setUserName(user.getName());
    userDto.setFirstName(user.getPerson().getFirstname());
    userDto.setLastName(user.getPerson().getLastname());
    List<String> roles = new ArrayList<>();
    user.getRoles().forEach(role -> roles.add(role.getName()));
    userDto.setUserRoles(roles);
    return userDto;
  }

}
