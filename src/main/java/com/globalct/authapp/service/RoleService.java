package com.globalct.authapp.service;

import com.globalct.authapp.model.Role;
import com.globalct.authapp.model.User;

import java.util.List;

public interface RoleService {

  Role createOrUpdateRole(Role role);

  Role getRole(String roleName);

  Iterable<Role> getAllRoles();

  void deleteRole(String roleName);

  void checkIfRoleExistsById(Long id);

  Role getRole(Long id);

  List<Role> setUserRoles(User user);

}
