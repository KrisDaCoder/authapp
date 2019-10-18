package com.globalct.authapp.controller;

import com.globalct.authapp.model.Role;
import com.globalct.authapp.service.implementation.MapValidationErrorService;
import com.globalct.authapp.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RoleController {

  private RoleService roleService;
  private MapValidationErrorService mapValidationErrorService;

  public RoleController(RoleService roleService, MapValidationErrorService mapValidationErrorService) {
    this.roleService = roleService;
    this.mapValidationErrorService = mapValidationErrorService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/all")
  public Iterable<Role> getAllRoles() {
    return roleService.getAllRoles();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{roleName}")
  public ResponseEntity<?> getRole(@PathVariable String roleName) {
    Role role = roleService.getRole(roleName);
    return new ResponseEntity<Role>(role, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("")
  public ResponseEntity<?> createOrUpdateRole(@Valid @RequestBody Role role, BindingResult bindingResult) {
    ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

    if (errorMap != null)
      return errorMap;

    return new ResponseEntity<Role>(roleService.createOrUpdateRole(role), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{roleName}")
  public ResponseEntity<?> deleteRole(@PathVariable String roleName) {
    roleService.deleteRole(roleName);
    return new ResponseEntity<String>("Role with name '" + roleName + "' was deleted", HttpStatus.NO_CONTENT);
  }

}
