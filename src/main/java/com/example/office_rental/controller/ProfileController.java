package com.example.office_rental.controller;

import com.example.office_rental.model.ApplicationStatus;
import com.example.office_rental.model.OfficeStatus;
import com.example.office_rental.model.User;
import com.example.office_rental.repository.OfficeSpaceRepository;
import com.example.office_rental.repository.RentalApplicationRepository;
import com.example.office_rental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    private final OfficeSpaceRepository officeSpaceRepository;

    private final RentalApplicationRepository applicationRepository;

    @GetMapping("/profile")
    public String profilePage(Model model) {

        User user = userService.getCurrentUser();

        model.addAttribute("user", user);

        return "profile/profile";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        long totalOffices =
                officeSpaceRepository.count();

        long freeOffices =
                officeSpaceRepository
                        .countByStatus(OfficeStatus.FREE);

        long occupiedOffices =
                officeSpaceRepository
                        .countByStatus(OfficeStatus.OCCUPIED);

        model.addAttribute(
                "totalOffices",
                totalOffices
        );

        model.addAttribute(
                "freeOffices",
                freeOffices
        );

        model.addAttribute(
                "occupiedOffices",
                occupiedOffices
        );

        long pendingApplications =
                applicationRepository.countByStatus(
                        ApplicationStatus.PENDING
                );

        long approvedApplications =
                applicationRepository.countByStatus(
                        ApplicationStatus.APPROVED
                );

        long rejectedApplications =
                applicationRepository.countByStatus(
                        ApplicationStatus.REJECTED
                );

        model.addAttribute(
                "pendingApplications",
                pendingApplications
        );

        model.addAttribute(
                "approvedApplications",
                approvedApplications
        );

        model.addAttribute(
                "rejectedApplications",
                rejectedApplications
        );

        return "admin/dashboard";
    }
}