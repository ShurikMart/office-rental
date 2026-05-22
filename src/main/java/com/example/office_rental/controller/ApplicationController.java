package com.example.office_rental.controller;

import com.example.office_rental.model.User;
import com.example.office_rental.repository.RentalApplicationRepository;
import com.example.office_rental.service.ApplicationService;
import com.example.office_rental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    private final RentalApplicationRepository applicationRepository;

    private final UserService userService;

    @PostMapping("/applications/create")
    public String createApplication(

            @RequestParam Long officeId,

            @RequestParam(required = false)
            String comment
    ) {

        applicationService.createApplication(
                officeId,
                comment
        );

        return "redirect:/profile";
    }

    @GetMapping("/applications/my")
    public String myApplications(Model model) {

        User user =
                userService.getCurrentUser();

        model.addAttribute(
                "applications",
                applicationRepository.findByUser(user)
        );

        return "applications/my-applications";
    }

    @GetMapping("/admin/applications")
    public String adminApplications(Model model) {

        model.addAttribute(
                "applications",
                applicationRepository.findAll()
        );

        return "admin/applications";
    }

    @PostMapping("/admin/applications/{id}/approve")
    public String approve(@PathVariable Long id) {

        applicationService.approve(id);

        return "redirect:/admin/applications";
    }

    @PostMapping("/admin/applications/{id}/reject")
    public String reject(
            @PathVariable Long id
    ) {

        applicationService.reject(id);

        return "redirect:/admin/applications";
    }
}