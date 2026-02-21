package com.tcs.user_auth_management.model.entity;

import com.tcs.user_auth_management.emuns.AuthenticationStatus;
import com.tcs.user_auth_management.model.entity.user.UserAuth;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "login_audit")
@Data
public class LoginAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Reference to the user who logged in
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_auth_id", nullable = false)
  private UserAuth userAuth;

  @Column(name = "ip_address", length = 45, nullable = false)
  private String ipAddress; // IPv4 or IPv6

  @Column(name = "user_agent", length = 500)
  private String userAgent; // browser/device info

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 20, nullable = false)
  private AuthenticationStatus status; // SUCCESS / FAILURE / LOCKED

  @Column(name = "login_time")
  private LocalDateTime loginTime;

  @Column(name = "logout_time")
  private LocalDateTime logoutTime;

  private String countryName;
  private String countryCode;
  private String region;
  private String city;

  @ColumnDefault("0.0")
  @Column(name = "latitude", nullable = false)
  private double latitude = 0.0;

  @ColumnDefault("0.0")
  @Column(name = "longitude", nullable = false)
  private double longitude = 0.0;

  private String timeZone;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LoginAudit that)) return false;
    return Objects.equals(id, that.id);
  }


  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : super.hashCode();
  }
}
