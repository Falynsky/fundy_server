package com.falynsky.fundy.JWT.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.falynsky.fundy.models.User;
import com.falynsky.fundy.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("ACCOUNT NOT FOUND!");
        }
        return user;
    }
}