package com.tcs.user_auth_management.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "cache_store")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheStore {
  @Id
  @Column(name = "cache_key", nullable = false)
  private String cacheKey;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "cache_value", nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> cacheValue;

  @Column(name = "expires_at")
  private Instant expiresAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  public CacheStore(String key, Map<String, Object> value, long ttlSecond) {
    this.cacheKey = key;
    this.cacheValue = value;
    if (ttlSecond > 0) {
      this.expiresAt = Instant.now().plusSeconds(ttlSecond);
    }
  }
}
