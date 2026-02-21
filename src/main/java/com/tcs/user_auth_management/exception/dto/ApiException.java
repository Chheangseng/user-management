package com.tcs.user_auth_management.exception.dto;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public record ApiException(
    String massage, int statusCode, HttpStatus status, ZonedDateTime zonedDateTime) {}
