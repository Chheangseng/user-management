package com.tcs.user_auth_management.model.mapper;

import com.tcs.user_auth_management.model.dto.DtoUserRequestInfo;
import com.tcs.user_auth_management.model.entity.LoginAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginAuditMapper {
    @Mapping(source = "ip", target = "ipAddress")
    LoginAudit toEntity(DtoUserRequestInfo dtoUserRequestInfo);
}
