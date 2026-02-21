package com.tcs.user_auth_management.model.entity.user;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public record UserSecurity(UserAuth userAccount) implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.userAccount.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getValue().toUpperCase()))
        .collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return this.userAccount.getPassword();
  }

  @Override
  public String getUsername() {
    return this.userAccount.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.userAccount.isActivate();
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.userAccount.isActivate();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.userAccount.isActivate();
  }

  @Override
  public boolean isEnabled() {
    return this.userAccount.isActivate();
  }

  public static Optional<UserSecurity> getUserSecurityContext() {
    return UserSecurity.getUserSecurityBYAuthentication(
        SecurityContextHolder.getContext().getAuthentication());
  }

  public static Optional<UserSecurity> getUserSecurityBYAuthentication(
      Authentication authentication) {
    if (authentication != null && authentication.getPrincipal() instanceof UserSecurity) {
      return Optional.of((UserSecurity) authentication.getPrincipal());
    }
    return Optional.empty(); // or throw exception if required
  }

  public static Authentication getAuthenticationByUserAuth(UserAuth userAuth) {
    var securityUser = new UserSecurity(userAuth);
    return new UsernamePasswordAuthenticationToken(
        securityUser, userAuth.getPassword(), securityUser.getAuthorities());
  }
}
