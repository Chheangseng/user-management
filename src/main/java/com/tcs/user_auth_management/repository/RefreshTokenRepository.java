package com.tcs.user_auth_management.repository;

import com.tcs.user_auth_management.model.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findBySessionId(String sessionId);

  @EntityGraph(attributePaths = "userAuth")
  Optional<RefreshToken> findWithUserAuthBySessionId(String sessionId);

}
