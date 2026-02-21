package com.tcs.user_auth_management.config.security.jwt;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa-key")
public record RSAProperties(RSAPrivateKey privateKey, RSAPublicKey publicKey) {}
