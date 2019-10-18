package com.globalct.authapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(generator = "generalPKGenerator")
  @GenericGenerator(name = "generalPKGenerator", strategy = "org.hibernate.id.UUIDGenerator")
  @Type(type = "uuid-char")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;
  @NotBlank(message = "First name is required")
  @Column(name = "person_firstname")
  private String firstname;
  @Column(name = "person_lastname")
  @NotBlank(message = "Last name is required")
  private String lastname;
  @NotNull(message = "Birthday is required")
  @JsonFormat(pattern = "dd-mm-yyyy")
  @Column(name = "person_birthday")
  private Date birthday;
  @NotNull(message = "Gender is required")
  @OneToOne
  @JoinColumn(name = "gender_id")
  private Gender gender;
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
