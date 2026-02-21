package com.tcs.user_auth_management.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoEmail(@Email @NotBlank(message = "email is required") String email) {}
