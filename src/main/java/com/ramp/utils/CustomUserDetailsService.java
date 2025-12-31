package com.ramp.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ramp.entity.Users;
import com.ramp.repo.UserRepo;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // fetch user from DB (use your Users entity)
        Users user = usersRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // map Users -> Spring Security UserDetails
        return new User(
                user.getUserName(),   // username field in your entity
                user.getPassword(),   // password field
                Collections.emptyList() // or map roles/authorities here
        );
    }
}
