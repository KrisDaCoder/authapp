package com.globalct.authapp.service.implementation;

import com.globalct.authapp.dto.UserDto;
import com.globalct.authapp.exception.IdException;
import com.globalct.authapp.exception.NameException;
import com.globalct.authapp.model.Role;
import com.globalct.authapp.util.ObjectMapper;
import com.globalct.authapp.model.Person;
import com.globalct.authapp.model.User;
import com.globalct.authapp.service.PersonService;
import com.globalct.authapp.service.RoleService;
import com.globalct.authapp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private PersonService personService;
  private BCryptPasswordEncoder encoder;
  private RoleService roleService;
  private ObjectMapper mapper;

  public UserServiceImpl(PersonService personService, BCryptPasswordEncoder encoder, RoleService roleService, ObjectMapper mapper) {
    this.personService = personService;
    this.encoder = encoder;
    this.roleService = roleService;
    this.mapper = mapper;
  }

  @Override
  public UserDto saveUser(User newUser) {
    try {
      UUID personId = newUser.getPerson().getId();
      if (personId == null) {
        throw new IdException("Person doesn't have a valid ID");
      }

      Person person = personService.getPerson(personId);

      newUser.getRoles().forEach(role -> roleService.checkIfRoleExistsById(role.getId()));

      newUser.setRoles(roleService.setUserRoles(newUser));

//      List<Role> userRoles = roleService.setUserRoles(newUser);

      newUser.setPassword(encoder.encode(newUser.getPassword()));
      newUser.setConfirmPassword("");

      person.setUser(newUser);
      newUser.setPerson(person);

      Person updatedPerson = personService.createOrUpdatePerson(person);

      return mapper.map(updatedPerson.getUser());
    } catch (Exception e) {
      if (e.getMessage().toLowerCase().matches(".*id.*")) {
        throw new IdException(e.getMessage());
      } else {
        throw new NameException("This username already exists");
      }

    }

  }

}
