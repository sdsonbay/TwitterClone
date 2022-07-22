package com.demo.controller;

import com.demo.response.ApiResponse;
import com.demo.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twitter")
public class FollowController {
    private final ApiResponse apiResponse;
    private final FollowService followService;

    @PostMapping(path = "follow/user/{userId}", produces = "application/json")
    public ResponseEntity<Object> addFollowee(Authentication authentication, @PathVariable("userId") Long userId) throws Exception{
        followService.followUser(authentication, userId);
        apiResponse.setMessage("Followee Added");
        apiResponse.setData(userId);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/unfollow/user/{userId}", produces = "application/json")
    public ResponseEntity<Object> deleteFollowee(Authentication authentication, @PathVariable("UserId") Long userId) throws Exception{
        followService.unfollowUser(authentication, userId);
        apiResponse.setMessage("Followee gone");
        apiResponse.setData(userId);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.CREATED);
    }
}
