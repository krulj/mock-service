package com.incode.simpleservice.third_party.repository;

import com.incode.simpleservice.third_party.repository.model.PremiumServiceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThirdPartyPremiumDao extends JpaRepository<PremiumServiceCompany, Long> {

  @Query("SELECT p FROM PremiumServiceCompany p WHERE LOWER(p.companyIdentificationNumber) like LOWER(CONCAT('%', :id, '%'))")
  List<PremiumServiceCompany> queryByIdentification(@Param("id") String query);
}
