package com.demo.controller;

import com.demo.dto.LoginDto;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.response.ApiResponse;
import com.demo.service.CustomUserDetailsService;
import com.demo.service.UserService;
import com.demo.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twitter")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final ApiResponse apiResponse;
    private final LoginDto loginDto;
    private final UserRepository userRepository;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> register(@RequestBody User user) throws Exception{
        User newUser = userService.createNewUser(user);
        apiResponse.setMessage("User created");
        apiResponse.setData(newUser);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> authenticateUser(@RequestBody User user){
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        authenticationManager.authenticate(auth);

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        User loggedInUser = userRepository.findByUsername(user.getUsername());

        loginDto.setJwt(jwt);
        loginDto.setUsername(user.getUsername());
        loginDto.setUserId(user.getId());

        apiResponse.setData(loginDto);
        apiResponse.setMessage("Auth token");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

}
