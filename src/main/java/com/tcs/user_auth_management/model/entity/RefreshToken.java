package com.tcs.user_auth_management.model.entity;

import com.tcs.user_auth_management.model.entity.common.Auditable;
import com.tcs.user_auth_management.model.entity.user.UserAuth;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;
import lombok.Data;

@Data
@Entity
@Table(
    indexes = {
      @Index(name = "idx_session_id", columnList = "sessionId"),
    })
public class RefreshToken extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Instant expiryDate;

  @Column(nullable = false, unique = true)
  private String sessionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_auth_id", nullable = false)
  private UserAuth userAuth;

  @Column(nullable = false)
  private boolean invoked = false;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RefreshToken that)) return false;
    return Objects.equals(id, that.id);
  }


  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : super.hashCode();
  }
}
