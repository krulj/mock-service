package com.incode.simpleservice.third_party.repository.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "premium_service_companies")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class) // Use same class as entity to speed up deserialization from json
public class PremiumServiceCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // By assignment CIN is not guaranteed to be unique

  private String companyIdentificationNumber;
  private String companyName;
  private String registrationDate;
  private String fullAddress;
  private Boolean isActive;

  public PremiumServiceCompany() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCompanyIdentificationNumber() {
    return companyIdentificationNumber;
  }

  public void setCompanyIdentificationNumber(String companyIdentificationNumber) {
    this.companyIdentificationNumber = companyIdentificationNumber;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getFullAddress() {
    return fullAddress;
  }

  public void setFullAddress(String companyFullAddress) {
    this.fullAddress = companyFullAddress;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean active) {
    isActive = active;
  }
}
