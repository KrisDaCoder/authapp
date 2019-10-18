package com.globalct.authapp.service;

import com.globalct.authapp.model.Person;

import java.util.UUID;

public interface PersonService {

  Person createOrUpdatePerson(Person person);

  Person getPerson(UUID personId);

  Iterable<Person> getAllPersons();

  void deletePerson(UUID personId);

}
