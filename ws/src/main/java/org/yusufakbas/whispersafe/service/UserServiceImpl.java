package org.yusufakbas.whispersafe.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.yusufakbas.whispersafe.config.TokenProvider;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.UpdateUserRequestDto;
import org.yusufakbas.whispersafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Users findUserById(Long id) throws UserException {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("User not found with id " + id);
    }

    @Override
    public Users findUserProfile(String jwt) throws UserException {
        String email = tokenProvider.getEmailFromToken(jwt);
        if (email == null) {
            throw new BadCredentialsException("Invalid token");
        }
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email" + email);
        }
        return user;
    }

    @Override
    public Users updateUser(Long userId, UpdateUserRequestDto updateUserRequest) throws UserException {
        Users user = findUserById(userId);
        System.out.println("test");
        if (updateUserRequest.username() != null && !updateUserRequest.username().isEmpty()) {
            user.setUsername(updateUserRequest.username());
        }
        if (updateUserRequest.profileImage() != null && !updateUserRequest.profileImage().isEmpty()) {
            user.setProfileImage(updateUserRequest.profileImage());
        }
        return userRepository.save(user);
    }

    @Override
    public List<Users> searchUser(String query) {
        List<Users> users = userRepository.searchUser(query);
        return users;
    }

}
