package com.incode.simpleservice.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("company.third-party")
public class CompanyServiceProperties {

  private String freeUrl;
  private String premiumUrl;

  public String getFreeUrl() {
    return freeUrl;
  }

  public void setFreeUrl(String freeUrl) {
    this.freeUrl = freeUrl;
  }

  public String getPremiumUrl() {
    return premiumUrl;
  }

  public void setPremiumUrl(String premiumUrl) {
    this.premiumUrl = premiumUrl;
  }
}
