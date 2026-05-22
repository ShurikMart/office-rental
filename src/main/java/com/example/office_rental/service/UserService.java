package com.example.office_rental.service;

import com.example.office_rental.model.User;
import com.example.office_rental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        String username = SecurityContextHolder

                .getContext()

                .getAuthentication()

                .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }
}