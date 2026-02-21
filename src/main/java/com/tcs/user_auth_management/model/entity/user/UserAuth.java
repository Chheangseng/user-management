package com.tcs.user_auth_management.model.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcs.user_auth_management.emuns.Role;
import com.tcs.user_auth_management.model.entity.LoginAudit;
import com.tcs.user_auth_management.model.entity.RefreshToken;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(
    name = "user_auth",
    indexes = {@Index(name = "idx_userauth_username", columnList = "username")})
public class UserAuth {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  private String fullName;

  @Column(unique = true, nullable = false)
  private String email;

  @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  private Set<Role> roles = new HashSet<>();

  private boolean activate = true;

  private boolean emailVerified = false;

  @Column(nullable = false, columnDefinition = "int default 0")
  private int risk = 0;

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      mappedBy = "userAuth")
  @JsonIgnore
  private Set<LoginAudit> loginAudits = new HashSet<>();

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      mappedBy = "userAuth")
  @JsonIgnore
  private Set<RefreshToken> refreshTokens = new HashSet<>();

  public void addRole(Role role) {
    if (roles != null) {
      roles.add(role);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAuth that)) return false;
    return Objects.equals(id, that.id);
  }


  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : super.hashCode();
  }
}
