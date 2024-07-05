package org.yusufakbas.whispersafe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.UpdateUserRequestDto;
import org.yusufakbas.whispersafe.response.ApiResponse;
import org.yusufakbas.whispersafe.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private UserController UserController;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Users> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        Users users = userService.findUserProfile(token);

        return new ResponseEntity<Users>(users, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<Users>> searchUsersHandler(@PathVariable("query") String query) throws UserException {
        List<Users> users = userService.searchUser(query);

        return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequestDto requestDto, @RequestHeader("Authorization") String token) throws UserException {
        Users user = userService.findUserProfile(token);
        userService.updateUser(user.getId(), requestDto);

        ApiResponse response = new ApiResponse("user updated successfully", true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
    }

}
