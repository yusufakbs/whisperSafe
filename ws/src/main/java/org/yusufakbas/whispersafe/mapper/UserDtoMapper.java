package org.yusufakbas.whispersafe.mapper;

import org.yusufakbas.whispersafe.dto.UserDto;
import org.yusufakbas.whispersafe.model.Users;

import java.util.HashSet;
import java.util.Set;

public class UserDtoMapper {

    public static UserDto toUserDto(Users user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImage());
    }

    public static HashSet<UserDto> toUserDtos(Set<Users> users) {
        HashSet<UserDto> userDtos = new HashSet<>();
        for (Users user : users) {
            userDtos.add(toUserDto(user));
        }
        return userDtos;
    }
}
