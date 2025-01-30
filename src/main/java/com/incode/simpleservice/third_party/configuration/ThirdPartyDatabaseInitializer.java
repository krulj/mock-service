package com.incode.simpleservice.third_party.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incode.simpleservice.third_party.repository.ThirdPartyFreeDao;
import com.incode.simpleservice.third_party.repository.ThirdPartyPremiumDao;
import com.incode.simpleservice.third_party.repository.model.FreeServiceCompany;
import com.incode.simpleservice.third_party.repository.model.PremiumServiceCompany;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ThirdPartyDatabaseInitializer {

  private static final Logger logger = LoggerFactory.getLogger(ThirdPartyDatabaseInitializer.class);

  private final ThirdPartyFreeDao freeDao;
  private final ThirdPartyPremiumDao premiumDao;

  public ThirdPartyDatabaseInitializer(ThirdPartyFreeDao freeDao, ThirdPartyPremiumDao premiumDao) {
    this.freeDao = freeDao;
    this.premiumDao = premiumDao;
  }

  @PostConstruct
  public void initializeDatabases(){
    ObjectMapper objectMapper = new ObjectMapper();

    try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api/free_service_companies-1.json")) {
      List<FreeServiceCompany> freeItems = objectMapper.readValue(inputStream, new TypeReference<>() {});
      freeDao.saveAll(freeItems);
    } catch (IOException e){
      logger.error("Error getting resources for database. ", e);
    }

    try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api/premium_service_companies-1.json")) {
      List<PremiumServiceCompany> premiumItems = objectMapper.readValue(inputStream, new TypeReference<>() {});
      premiumDao.saveAll(premiumItems);
    } catch (IOException e){
      logger.error("Error getting resources for database. ", e);
    }
  }
}
