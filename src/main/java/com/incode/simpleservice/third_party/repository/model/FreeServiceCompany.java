package com.incode.simpleservice.third_party.repository.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Use same class as entity to speed up deserialization from json
public class FreeServiceCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // By assignment CIN is not guaranteed to be unique

  private String cin;
  private String name;
  private String registrationDate;
  private String address;
  private Boolean isActive;

  public FreeServiceCompany() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCin() {
    return cin;
  }

  public void setCin(String cin) {
    this.cin = cin;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean active) {
    isActive = active;
  }
}
