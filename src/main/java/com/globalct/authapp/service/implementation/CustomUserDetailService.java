package com.globalct.authapp.service.implementation;

import com.globalct.authapp.model.User;
import com.globalct.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByName(userName);
    if (!userOptional.isPresent()) {
      new UsernameNotFoundException("User not found");
    }
    return userOptional.get();
  }

  @Transactional
  public User loadUserById(UUID id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (!userOptional.isPresent()) {
      new UsernameNotFoundException("User not found");
    }
    return userOptional.get();
  }

}
