package com.globalct.authapp.service;

import com.globalct.authapp.model.Gender;

public interface GenderService {

  Gender createOrUpdateGender(Gender gender);

  Gender getGender(String genderName);

  Iterable<Gender> getAllGenders();

  void deleteGender(String genderName);

  void checkIfGenderExistsById(Long id);

}
