package com.globalct.authapp.service.implementation;

import com.globalct.authapp.exception.IdException;
import com.globalct.authapp.exception.NameException;
import com.globalct.authapp.model.Role;
import com.globalct.authapp.model.User;
import com.globalct.authapp.repository.RoleRepository;
import com.globalct.authapp.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role createOrUpdateRole(Role newRole) {
    try {
      Long id = newRole.getId();
      if (id != null) {
        checkIfRoleExistsById(id);
      }
      return roleRepository.save(newRole);
    } catch (Exception e) {
      if (e.getMessage().toLowerCase().matches(".*id.*")) {
        throw new IdException(e.getMessage());
      } else {
        throw new NameException("Role with name '" + newRole.getName() + "' already exists");
      }

    }
  }

  @Override
  public Role getRole(String roleName) {
    Optional<Role> roleOptional = roleRepository.findByName(roleName);
    if (!roleOptional.isPresent()) {
      throw new NameException("Role with name '" + roleName + "' doesn't exist");
    }
    return roleOptional.get();
  }

  @Override
  public Iterable<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  @Override
  public void deleteRole(String roleName) {
    Optional<Role> roleOptional = roleRepository.findByName(roleName);
    if (!roleOptional.isPresent()) {
      throw new NameException("Role with name '" + roleName + "' doesn't exist");
    }
    roleRepository.delete(roleOptional.get());
  }

  @Override
  public void checkIfRoleExistsById(Long id) {
    Optional<Role> roleOptional = roleRepository.findById(id);
    if (!roleOptional.isPresent()) {
      throw new IdException("Role with id '" + id.toString() + "' doesn't exist");
    }
  }

  @Override
  public Role getRole(Long id) {
    Optional<Role> roleOptional = roleRepository.findById(id);
    if (!roleOptional.isPresent()) {
      throw new IdException("Role with id '" + id.toString() + "' doesn't exist");
    }
    return roleOptional.get();
  }

  @Override
  public List<Role> setUserRoles(User user) {
    List<Role> userRoles = user.getRoles().stream().map(role -> {
      Role fullRole = getRole(role.getId());
      return fullRole;
    }).collect(Collectors.toList());

    return userRoles;
  }
}
