package com.globalct.authapp.repository;

import com.globalct.authapp.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

  Optional<Person> findById(UUID personId);

}
