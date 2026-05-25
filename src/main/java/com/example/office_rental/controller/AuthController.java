package com.example.office_rental.controller;

import com.example.office_rental.dto.RegisterDto;
import com.example.office_rental.model.Role;
import com.example.office_rental.model.User;
import com.example.office_rental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {

        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegisterDto());

        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("user")
            RegisterDto dto
    ) {

        User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()), Role.USER);

        userRepository.save(user);

        return "redirect:/login";
    }
}