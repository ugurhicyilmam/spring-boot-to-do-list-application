package com.ugurhicyilmam.service;

import com.ugurhicyilmam.controller.request.RegistrationRequest;
import com.ugurhicyilmam.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User register(RegistrationRequest request);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findByUsername(String username);
}
