package com.tcs.user_auth_management.model.dto;

public record DtoJwtTokenResponse(
    String accessToken, long expireIn, String refreshToken, long refreshExpireIn) {}
