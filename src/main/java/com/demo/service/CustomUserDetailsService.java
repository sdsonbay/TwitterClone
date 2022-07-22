package com.demo.service;

import com.demo.repository.UserRepository;
import com.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.demo.model.User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
