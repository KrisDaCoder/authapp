package com.globalct.authapp.repository;

import com.globalct.authapp.model.Gender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends CrudRepository<Gender, Long> {

  Optional<Gender> findByName(String genderName);
  Optional<Gender> deleteByName(String genderName);

}
