package com.globalct.authapp.repository;

import com.globalct.authapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

  Optional<User> findByName(String userName);
  Optional<User> findById(UUID id);

}
