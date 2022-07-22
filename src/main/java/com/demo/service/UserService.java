package com.demo.service;

import com.demo.dto.Tweeter;
import com.demo.dto.TweetersList;
import com.demo.model.Follower;
import com.demo.model.User;
import com.demo.repository.FollowerRepository;
import com.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    public User createNewUser(User user) throws Exception{
        if(userExists(user)) throw new Exception("User already exists!");
        String userEmail = user.getEmail().trim();
        String userUsername = user.getUsername().trim();
        String userPassword = user.getPassword().trim();

        boolean usernameIsNotValid = (userUsername == null) || !userUsername.matches("[A-Za-z0-9_]+");
        boolean emailIsNotValid = (userEmail == null) || !userEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        boolean passwordIsNotValid = (userPassword == null) || !userPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}");

        if(usernameIsNotValid) throw new Exception("Username not set or not valid!");
        if(emailIsNotValid) throw new Exception("Email not set or not valid!");
        if(passwordIsNotValid) throw new Exception("Password not set or not valid!");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User changePassword(User user) throws Exception{
        String userPassword = user.getPassword().trim();

        boolean passwordIsNotValid = (userPassword == null) || !userPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}");
        if (passwordIsNotValid) throw new Exception("Password not set or not valid");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public TweetersList listUsers(Authentication authentication){
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        List<User> userList = userRepository.findByUsernameNot(loggedInUser.getUsername());

        TweetersList tweetersList = new TweetersList();
        for(User user: userList){
            Tweeter tweeter = new Tweeter();
            tweeter.userId = user.getId();
            tweeter.username = user.getUsername();
            tweeter.email = user.getEmail();

            Follower f = followerRepository.findByFolloweeAndFollower(user, loggedInUser).orElse(null);
            if (f == null){
                tweeter.isFollowedByUser = false;
            }else {
                tweeter.isFollowedByUser = true;
            }
            tweetersList.tweetersList.add(tweeter);
        }
        return tweetersList;
    }

    private Boolean userExists(User user){
        if(userRepository.findByUsername(user.getUsername()) != null) return true;
        if(userRepository.findByEmail(user.getEmail()) != null) return true;
        return false;
    }
}
