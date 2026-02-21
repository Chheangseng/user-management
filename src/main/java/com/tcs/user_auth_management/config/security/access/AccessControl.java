package com.tcs.user_auth_management.config.security.access;

import com.tcs.user_auth_management.emuns.Role;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessControl {
    Role[] role() default {};
}
