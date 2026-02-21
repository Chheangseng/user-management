package com.tcs.user_auth_management.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public record DtoResetPassword(
    @NotBlank(message = "Reset token is required") String resetToken,
    @NotBlank(message = "New Password is required") String newPassword) {}
