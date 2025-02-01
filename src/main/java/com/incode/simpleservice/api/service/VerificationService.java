package com.incode.simpleservice.api.service;

import com.incode.simpleservice.api.dto.VerificationDTO;
import com.incode.simpleservice.api.exception.BadRequestException;
import com.incode.simpleservice.api.exception.ServiceErrorCodes;
import com.incode.simpleservice.api.manipulator.VerificationManipulator;
import com.incode.simpleservice.api.repository.VerificationDAO;
import com.incode.simpleservice.api.repository.entity.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

  private static final Logger logger = LoggerFactory.getLogger(VerificationService.class);

  private VerificationDAO verificationDAO;

  public VerificationService(VerificationDAO verificationDAO) {
    this.verificationDAO = verificationDAO;
  }

  public VerificationDTO saveVerificationData(Verification verification) {
    logger.info("Saving verification data: {}", verification);
    try {
      Verification result = verificationDAO.save(verification);
      return VerificationManipulator.toDto(result);
    } catch (DataIntegrityViolationException e) {
      logger.error("Saving verification data failed. Non unique verification id '{}'", verification.getVerificationId());
      throw new BadRequestException(ServiceErrorCodes.VERIFICATION_NON_UNIQUE);
    }
  }

  public VerificationDTO getByVerificationId(String id) {
    logger.info("Getting verification by id: {}", id);
    Verification verification = verificationDAO.getByVerificationId(id);
    if (verification == null) {
      throw new BadRequestException(ServiceErrorCodes.VERIFICATION_NOT_FOUND);
    }
    return VerificationManipulator.toDto(verification);
  }

}
