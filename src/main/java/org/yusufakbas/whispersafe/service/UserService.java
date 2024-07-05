package org.yusufakbas.whispersafe.service;

import org.springframework.stereotype.Service;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.UpdateUserRequestDto;

import java.util.List;

@Service
public interface UserService {

    public Users findUserById(Long id) throws UserException;

    public Users findUserProfile(String jwt) throws UserException;

    public Users updateUser(Long userId, UpdateUserRequestDto updateUserRequest) throws UserException;

    public List<Users> searchUser(String query);

}
