package com.tcs.user_auth_management.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public record DtoUserLogin(
    @NotBlank(message = "Username must not be blank") String username,
    @NotBlank(message = "Password must not be blank") String password) {}
