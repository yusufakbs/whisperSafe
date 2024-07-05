package org.yusufakbas.whispersafe.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yusufakbas.whispersafe.config.TokenProvider;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.repository.UserRepository;
import org.yusufakbas.whispersafe.request.LoginRequest;
import org.yusufakbas.whispersafe.response.AuthResponse;
import org.yusufakbas.whispersafe.service.CustomUserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, CustomUserService customUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserService = customUserService;
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody Users user) throws UserException {
        String email = user.getEmail();
        String full_name = user.getFullName();
        String password = user.getPassword();

        Users isUser = userRepository.findByEmail(email);
        if (isUser != null) {
            throw new UserException("Email is used with another account" + email);
        }

        Users cratedUser = new Users();
        cratedUser.setEmail(email);
        cratedUser.setFullName(full_name);
        cratedUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(cratedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(jwt, true);
        return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);

    }


    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) throws BadRequestException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(jwt, true);
        return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);
    }


    public Authentication authenticate(String username, String password) throws BadRequestException {
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadRequestException("Username or password is incorrect");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadRequestException("Username or password is incorrect");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


    }

}
