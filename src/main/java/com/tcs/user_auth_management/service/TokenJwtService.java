package com.tcs.user_auth_management.service;

import com.tcs.user_auth_management.emuns.JwtTokenType;
import com.tcs.user_auth_management.exception.ApiExceptionStatusException;
import com.tcs.user_auth_management.model.dto.DtoJwtTokenResponse;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenJwtService {
  private final String issuer = "authentication-server";
  private final JwtEncoder encoder;
  private final JwtDecoder decoder;
  private final RefreshTokenSessionService refreshTokenSessionService;
  // 3 min
  private final long expireInSeconds = 120;

  public DtoJwtTokenResponse generateToken(Authentication authentication) {
    Instant now = Instant.now();
    return new DtoJwtTokenResponse(
        accessToken(authentication, now),
        expireInSeconds,
        refreshTokenSessionService.generateRefreshTokenSession(authentication, now),
        RefreshTokenSessionService.refreshTokenExpireInSeconds);
  }

  public DtoJwtTokenResponse refreshToken(Authentication authentication, Jwt jwt) {
    Instant now = Instant.now();
    return new DtoJwtTokenResponse(
        accessToken(authentication, now),
        expireInSeconds,
        refreshTokenSessionService.refreshTokenWithOldSession(authentication, jwt, now),
        RefreshTokenSessionService.refreshTokenExpireInSeconds);
  }

  private String accessToken(Authentication authentication, Instant now) {
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expireInSeconds))
            .subject(authentication.getName())
            .claim("scope", this.getScope(authentication))
            .claim("roles", this.getRoles(authentication))
            .claim("type", JwtTokenType.ACCESS.getType())
            .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public String resetToken(Authentication authentication) {
    Instant now = Instant.now();
    //  5 min
    long resetPasswordToken = 300;
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(resetPasswordToken))
            .subject(authentication.getName())
            .claim("type", JwtTokenType.RESET_PASSWORD.getType())
            .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
  public String generateVerifyEmailToken(Authentication authentication){
      Instant now = Instant.now();
      //  5 min
      long resetPasswordToken = 300;
      JwtClaimsSet claims =
              JwtClaimsSet.builder()
                      .issuer(issuer)
                      .issuedAt(now)
                      .expiresAt(now.plusSeconds(resetPasswordToken))
                      .subject(authentication.getName())
                      .claim("type", JwtTokenType.VERIFY_EMAIL.getType())
                      .build();
      return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public List<String> getRoles(Authentication authentication) {
    return authentication.getAuthorities().stream()
        .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith("ROLE_"))
        .map(auth -> auth.getAuthority().substring(5))
        .collect(Collectors.toList());
  }

  public List<String> getScope(Authentication authentication) {
    return authentication.getAuthorities().stream()
        .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith("SCOPE_"))
        .map(auth -> auth.getAuthority().substring(6))
        .collect(Collectors.toList());
  }

  public Jwt verifyResetPasswordToken(String resetToken) {
      var decode = verifyToken(resetToken);
      var type = validateType(decode);
      if (type != JwtTokenType.RESET_PASSWORD) {
        throw new ApiExceptionStatusException("Incorrect Token Type or format", 401);
      }
      return decode;
  }

  public Jwt verifyEmailToken(String token) {
      var decode = verifyToken(token);
      var type = validateType(decode);
      if (type != JwtTokenType.VERIFY_EMAIL) {
          throw new ApiExceptionStatusException("Incorrect Token Type or format", 401);
      }
      return decode;
  }

  public Jwt verifyToken(String token) {
    try {
      return decoder.decode(token);
    } catch (JwtException e) {
      throw new ApiExceptionStatusException(e.getMessage(), 400, e);
    }
  }

  private JwtTokenType validateType(Jwt jwt) {
    if (Objects.isNull(jwt))
      throw new ApiExceptionStatusException("Incorrect Token Type or format", 401);
    return JwtTokenType.fromType(jwt.getClaim("type"))
        .orElseThrow(() -> new ApiExceptionStatusException("Incorrect Token Type or format", 401));
  }

  public Map<String, Object> introspect(String token) {
    try {
      Jwt jwt = verifyToken(token);
      return Map.of(
          "active",
          true,
          "iss",
          jwt.getIssuer(),
          "sub",
          jwt.getSubject(),
          "exp",
          jwt.getExpiresAt() != null ? jwt.getExpiresAt().getEpochSecond() : 0,
          "iat",
          jwt.getIssuedAt() != null ? jwt.getIssuedAt().getEpochSecond() : 0,
          "scope",
          jwt.getClaimAsString("scope"));
    } catch (JwtException e) {
      // Token invalid or expired
      return Map.of("active", false);
    }
  }

  public Jwt verifyRefreshToken(String refreshToken) {
    return refreshTokenSessionService.verifyRefreshToken(refreshToken);
  }
}
