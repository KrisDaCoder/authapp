package com.globalct.authapp.bootstrap;

import com.globalct.authapp.model.Gender;
import com.globalct.authapp.model.Person;
import com.globalct.authapp.model.Role;
import com.globalct.authapp.repository.GenderRepository;
import com.globalct.authapp.repository.PersonRepository;
import com.globalct.authapp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DbSeeder implements CommandLineRunner {

  private GenderRepository genderRepository;
  private PersonRepository personRepository;
  private RoleRepository roleRepository;

  public DbSeeder(GenderRepository genderRepository, PersonRepository personRepository, RoleRepository roleRepository) {
    this.genderRepository = genderRepository;
    this.personRepository = personRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    // populating the gender table
    Gender gender = new Gender();
    gender.setName("MALE");

    Gender gender1 = new Gender();
    gender1.setName("FEMALE");

    Gender gender2 = new Gender();
    gender2.setName("DIVERSE");

    genderRepository.save(gender);
    genderRepository.save(gender1);
    genderRepository.save(gender2);

    // populating the person table
    Person person = new Person();
    person.setFirstname("John");
    person.setLastname("Doe");
    person.setBirthday(new Date());
    person.setGender(gender);

    personRepository.save(person);

    Person person1 = new Person();
    person1.setFirstname("Jane");
    person1.setLastname("Doe");
    person1.setBirthday(new Date());
    person1.setGender(gender1);

    personRepository.save(person1);

    // populating the rule table
    Role role = new Role();
    role.setName("ADMIN");

    // populating the rule table
    Role role1 = new Role();
    role1.setName("USER");

    roleRepository.save(role);
    roleRepository.save(role1);

  }

}
