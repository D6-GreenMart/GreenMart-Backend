package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.UserDto;
import com.greenmart.app.domain.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-08T02:20:06+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.40.0.v20241112-0530, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.address( user.getAddress() );
        userDto.createdAt( user.getCreatedAt() );
        userDto.email( user.getEmail() );
        userDto.id( user.getId() );
        userDto.imagePath( user.getImagePath() );
        userDto.name( user.getName() );
        userDto.phoneNumber( user.getPhoneNumber() );
        userDto.role( user.getRole() );

        return userDto.build();
    }

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.address( userDto.getAddress() );
        user.createdAt( userDto.getCreatedAt() );
        user.email( userDto.getEmail() );
        user.id( userDto.getId() );
        user.imagePath( userDto.getImagePath() );
        user.name( userDto.getName() );
        user.phoneNumber( userDto.getPhoneNumber() );
        user.role( userDto.getRole() );

        return user.build();
    }
}
