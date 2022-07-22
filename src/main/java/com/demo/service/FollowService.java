package com.demo.service;

import com.demo.model.Follower;
import com.demo.model.User;
import com.demo.repository.FollowerRepository;
import com.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    public void followUser(Authentication authentication, Long userId) throws Exception{
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        User followeeUser = userRepository.findById(userId).orElse(null);
        if (followeeUser == null){
            throw new Exception("User not found");
        }
        Follower f = new Follower();
        f.setFollower(loggedInUser);
        f.setFollowee(followeeUser);
        followerRepository.save(f);
    }

    public void unfollowUser(Authentication authentication, Long userId) throws Exception{
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        User followeeUser = userRepository.findById(userId).orElse(null);
        if (followeeUser == null){
            throw new Exception("User not found");
        }
        Follower f = followerRepository.findByFolloweeAndFollower(followeeUser, loggedInUser).orElse(null);
        if(f == null){
            throw new Exception("User not following " + followeeUser.getUsername());
        }
        followerRepository.delete(f);
    }
}
