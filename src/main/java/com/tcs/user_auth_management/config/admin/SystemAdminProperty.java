package com.tcs.user_auth_management.config.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system.admin-properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemAdminProperty {
    String email;
    String firstName;
    String lastName;
    String password;
}
