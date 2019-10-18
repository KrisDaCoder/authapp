package com.globalct.authapp.service.implementation;

import com.globalct.authapp.exception.IdException;
import com.globalct.authapp.exception.NameException;
import com.globalct.authapp.model.Gender;
import com.globalct.authapp.repository.GenderRepository;
import com.globalct.authapp.service.GenderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenderServiceImpl implements GenderService {

  private GenderRepository genderRepository;

  public GenderServiceImpl(GenderRepository genderRepository) {
    this.genderRepository = genderRepository;
  }

  @Override
  public Gender createOrUpdateGender(Gender newGender) {
    try {
      Long id = newGender.getId();
      if (id != null) {
        checkIfGenderExistsById(id);
      }
      return genderRepository.save(newGender);
    } catch (Exception e) {
      if (e.getMessage().toLowerCase().matches(".*id.*")) {
        throw new IdException(e.getMessage());
      } else {
        throw new NameException("Gender with name '" + newGender.getName() + "' already exists");
      }
    }
  }

  @Override
  public Gender getGender(String genderName) {
    Optional<Gender> genderOptional = genderRepository.findByName(genderName);
    if (!genderOptional.isPresent()) {
      throw new NameException("Gender with name '" + genderName + "' doesn't exist ");
    }
    return genderOptional.get();
  }

  @Override
  public Iterable<Gender> getAllGenders() {
    return genderRepository.findAll();
  }

  @Override
  public void deleteGender(String genderName) {
    Optional<Gender> genderOptional = genderRepository.findByName(genderName);
    if (!genderOptional.isPresent()) {
      throw new NameException("Gender with name '" + genderName + "' doesn't exist ");
    }
    genderRepository.deleteByName(genderName);
  }

  @Override
  public void checkIfGenderExistsById(Long id) {
    Optional<Gender> genderOptional = genderRepository.findById(id);
    if (!genderOptional.isPresent()) {
      throw new IdException("Gender with id '" + id.toString() + "' doesn't exist");
    }
  }

}
