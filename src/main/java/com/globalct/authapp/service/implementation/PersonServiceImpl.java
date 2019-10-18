package com.globalct.authapp.service.implementation;

import com.globalct.authapp.exception.IdException;
import com.globalct.authapp.model.Person;
import com.globalct.authapp.repository.PersonRepository;
import com.globalct.authapp.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

  private PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public Person createOrUpdatePerson(Person newPerson) {
    UUID id = newPerson.getId();
    if (id != null) {
      Optional<Person> personOptional = personRepository.findById(id);
      if (!personOptional.isPresent()) {
        throw new IdException("Person with id '" + id.toString() + "' doesn't exist");
      }
    }
    return personRepository.save(newPerson);
  }

  @Override
  public Person getPerson(UUID personId) {
    Optional<Person> personOptional = personRepository.findById(personId);
    if (!personOptional.isPresent()) {
      throw new IdException("Person with id '" + personId.toString() + "' doesn't exist");
    }
    return personOptional.get();
  }

  @Override
  public Iterable<Person> getAllPersons() {
    return personRepository.findAll();
  }

  @Override
  public void deletePerson(UUID personId) {
    Optional<Person> personOptional = personRepository.findById(personId);
    if (!personOptional.isPresent()) {
      throw new IdException("Person with id '" + personId.toString() + "' doesn't exist");
    }
    personRepository.delete(personOptional.get());
  }

}
