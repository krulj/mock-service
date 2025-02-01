package com.incode.simpleservice.api.service;

import com.incode.simpleservice.api.dto.VerificationDTO;
import com.incode.simpleservice.api.manipulator.VerificationManipulator;
import com.incode.simpleservice.api.repository.VerificationDAO;
import com.incode.simpleservice.api.repository.entity.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

@Service
public class VerificationService {
  private static final Logger logger = LoggerFactory.getLogger(VerificationService.class);

  private VerificationDAO verificationDAO;

  public VerificationService(VerificationDAO verificationDAO) {
    this.verificationDAO = verificationDAO;
  }

  public VerificationDTO saveVerificationData(Verification verification) {
    logger.info("Saving verification data: {}", verification);
    Verification result = verificationDAO.save(verification);
    return VerificationManipulator.toDto(result);
  }

  public VerificationDTO getByVerificationId(String id) {
    logger.info("Getting verification by id: {}", id);
    Verification verification = verificationDAO.getByVerificationId(id);
    return VerificationManipulator.toDto(verification);
  }

}
