package com.globalct.authapp.controller;

import com.globalct.authapp.model.Person;
import com.globalct.authapp.service.implementation.MapValidationErrorService;
import com.globalct.authapp.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/person")
public class PersonController {

  private PersonService personService;
  private MapValidationErrorService mapValidationErrorService;

  public PersonController(PersonService personService, MapValidationErrorService mapValidationErrorService) {
    this.personService = personService;
    this.mapValidationErrorService = mapValidationErrorService;
  }

  @GetMapping("/all")
  public Iterable<Person> getAllPersons() {
    return personService.getAllPersons();
  }

  @GetMapping("/{personId}")
  public ResponseEntity<?> getPerson(@PathVariable String personId) {
    Person person = personService.getPerson(UUID.fromString(personId));
    return new ResponseEntity<Person>(person, HttpStatus.OK);

  }

  @PostMapping("")
  public ResponseEntity<?> createOrUpdatePerson(@Valid @RequestBody Person person, BindingResult bindingResult) {
    ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

    if (errorMap != null)
      return errorMap;

    return new ResponseEntity<Person>(personService.createOrUpdatePerson(person), HttpStatus.CREATED);
  }

  @DeleteMapping("/{personId}")
  public ResponseEntity<?> deletePerson(@PathVariable String personId) {
    personService.deletePerson(UUID.fromString(personId));
    return new ResponseEntity<String>("Person with name '" + personId + "' was deleted", HttpStatus.NO_CONTENT);
  }

}
