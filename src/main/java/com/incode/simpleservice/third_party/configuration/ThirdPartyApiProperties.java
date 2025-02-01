package com.incode.simpleservice.third_party.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("third-party.mock")
public class ThirdPartyApiProperties {

  private String freeFailureRate;
  private String premiumFailureRate;
  private String freeInitFile;
  private String premiumInitFile;

  public String getFreeFailureRate() {
    return freeFailureRate;
  }

  public void setFreeFailureRate(String freeFailureRate) {
    this.freeFailureRate = freeFailureRate;
  }

  public String getPremiumFailureRate() {
    return premiumFailureRate;
  }

  public void setPremiumFailureRate(String premiumFailureRate) {
    this.premiumFailureRate = premiumFailureRate;
  }

  public String getFreeInitFile() {
    return freeInitFile;
  }

  public void setFreeInitFile(String freeInitFile) {
    this.freeInitFile = freeInitFile;
  }

  public String getPremiumInitFile() {
    return premiumInitFile;
  }

  public void setPremiumInitFile(String premiumInitFile) {
    this.premiumInitFile = premiumInitFile;
  }
}
