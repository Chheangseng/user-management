package com.tcs.user_auth_management.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.tcs.user_auth_management.service.TokenJwtService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known")
@AllArgsConstructor
public class Oauth2Controller {
  private final JWKSet jwkSet;
  private final TokenJwtService tokenJwtService;

  @GetMapping("/jwks.json")
  public Map<String, Object> getKeys() {
    return jwkSet.toJSONObject();
  }

  @PostMapping("/introspect")
  public ResponseEntity<Map<String, Object>> introspect(@RequestParam("token") String token) {
    return ResponseEntity.ok(tokenJwtService.introspect(token));
  }
}
