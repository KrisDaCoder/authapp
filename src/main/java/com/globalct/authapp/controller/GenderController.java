package com.globalct.authapp.controller;

import com.globalct.authapp.model.Gender;
import com.globalct.authapp.service.GenderService;
import com.globalct.authapp.service.implementation.MapValidationErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/gender")
public class GenderController {

  private GenderService genderService;
  private MapValidationErrorService mapValidationErrorService;

  public GenderController(GenderService genderService, MapValidationErrorService mapValidationErrorService) {
    this.genderService = genderService;
    this.mapValidationErrorService = mapValidationErrorService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/all")
  public Iterable<Gender> getAllGenders() {
    return genderService.getAllGenders();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{genderName}")
  public ResponseEntity<?> getGender(@PathVariable String genderName) {
    Gender gender = genderService.getGender(genderName);
    return new ResponseEntity<Gender>(gender, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("")
  public ResponseEntity<?> createOrUpdateGender(@Valid @RequestBody Gender gender, BindingResult bindingResult) {
    ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

    if (errorMap != null)
      return errorMap;

    return new ResponseEntity<Gender>(genderService.createOrUpdateGender(gender), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{genderName}")
  public ResponseEntity<?> deleteGender(@PathVariable String genderName) {
    genderService.deleteGender(genderName);
    return new ResponseEntity<String>("Gender with name '" + genderName + "' was deleted", HttpStatus.NO_CONTENT);
  }

}
