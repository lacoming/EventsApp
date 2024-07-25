package com.github.lacoming.eventsapi.mapper;

import com.github.lacoming.eventsapi.entity.User;
import com.github.lacoming.eventsapi.web.dto.CreateUserRequest;
import com.github.lacoming.eventsapi.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    User toEntity(CreateUserRequest request);

    UserDto toDto(User user);
}
