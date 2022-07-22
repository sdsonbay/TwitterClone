package com.demo.controller;

import com.demo.dto.TweetersList;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.response.ApiResponse;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/twitter")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ApiResponse apiResponse;

    @GetMapping(path = "/user/list", produces = "application/json")
    public ResponseEntity<Object> listOfUsers(Authentication authentication){
        TweetersList userList = userService.listUsers(authentication);
        apiResponse.setData(userList);
        apiResponse.setMessage("User List");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @PostMapping(path = "/user/change-password", produces = "application/json")
    public ResponseEntity<Object> changePassword(Authentication authentication, @RequestBody User user) throws Exception{
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        user.setId(loggedInUser.getId());
        user.setEmail(loggedInUser.getEmail());
        user.setUsername(loggedInUser.getUsername());
        User updatedUser = userService.changePassword(user);
        apiResponse.setData(updatedUser);
        apiResponse.setMessage("Password changes");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

}
