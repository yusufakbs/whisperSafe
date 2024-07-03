package org.yusufakbas.whispersafe.service;

import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.model.request.UpdateUserRequest;

import java.util.List;

public interface UserService {

    public Users findUserById(Long id) throws UserException;

    public Users findUserProfile(String jwt) throws UserException;

    public Users updateUser(Long userId, UpdateUserRequest updateUserRequest) throws UserException;

    public List<Users> searchUser(String query);

}
