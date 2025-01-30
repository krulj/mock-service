package com.incode.simpleservice.third_party.repository;

import com.incode.simpleservice.third_party.repository.model.FreeServiceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThirdPartyFreeDao extends JpaRepository<FreeServiceCompany, Long> {

  @Query("SELECT f FROM FreeServiceCompany f WHERE LOWER(f.cin) like LOWER(CONCAT('%', :id, '%'))")
  List<FreeServiceCompany> queryByIdentification(@Param("id") String query);

}
