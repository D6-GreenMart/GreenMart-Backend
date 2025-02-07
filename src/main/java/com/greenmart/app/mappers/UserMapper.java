package com.greenmart.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.greenmart.app.domain.dtos.UserDto;
import com.greenmart.app.domain.entities.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}