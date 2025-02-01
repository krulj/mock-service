package com.incode.simpleservice.api.repository;

import com.incode.simpleservice.api.repository.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationDAO extends JpaRepository<Verification, Long> {

  @Query("SELECT v FROM Verification v WHERE v.verificationId = :id")
  Verification getByVerificationId(@Param("id") String id);

}
