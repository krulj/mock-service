package com.incode.simpleservice.api.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "verifications")
public class Verification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "verification_id", nullable = false, unique = true)
  private String verificationId;

  @Column(name = "query", nullable = false)
  private String query;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @Column(name = "result", columnDefinition = "JSON", nullable = false)
  private String result;

  @Column(name = "source")
  private String source;

  public Verification() {
  }

  private Verification(Builder builder) {
    setId(builder.id);
    setVerificationId(builder.verificationId);
    setQuery(builder.query);
    setTimestamp(builder.timestamp);
    setResult(builder.result);
    setSource(builder.source);
  }

  @Override
  public String toString() {
    return "Verification{" +
        "verificationId='" + verificationId + '\'' +
        ", query='" + query + '\'' +
        ", timestamp=" + timestamp +
        ", result='" + result + '\'' +
        ", source='" + source + '\'' +
        '}';
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getVerificationId() {
    return verificationId;
  }

  public void setVerificationId(String verificationId) {
    this.verificationId = verificationId;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }


  public static final class Builder {

    private Long id;
    private String verificationId;
    private String query;
    private LocalDateTime timestamp;
    private String result;
    private String source;

    private Builder() {
    }

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder verificationId(String val) {
      verificationId = val;
      return this;
    }

    public Builder query(String val) {
      query = val;
      return this;
    }

    public Builder timestamp(LocalDateTime val) {
      timestamp = val;
      return this;
    }

    public Builder result(String val) {
      result = val;
      return this;
    }

    public Builder source(String val) {
      source = val;
      return this;
    }

    public Verification build() {
      return new Verification(this);
    }
  }
}
