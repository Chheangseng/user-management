package com.tcs.user_auth_management.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoUserRegister(
    @NotBlank(message = "username is required") String username,
    @NotBlank(message = "username is required") String password,
    String fullName,
    @NotBlank(message = "username is required")
        @Email(message = "invalid email")
        @Schema(description = "User email address", example = "bob@example.com")
        String email) {}
