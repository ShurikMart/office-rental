package com.example.office_rental.controller;

import com.example.office_rental.model.User;
import com.example.office_rental.repository.RentalApplicationRepository;
import com.example.office_rental.service.ApplicationService;
import com.example.office_rental.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            String comment,

            RedirectAttributes redirectAttributes
    ) {

        try {
            applicationService.createApplication(officeId, comment);
            redirectAttributes.addFlashAttribute("success", "Application created");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/profile";
    }

    @GetMapping("/applications/my")
    public String myApplications(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("applications", applicationRepository.findByUser(user));

        return "applications/my-applications";
    }

    @GetMapping("/admin/applications")
    public String adminApplications(Model model) {
        model.addAttribute("applications", applicationRepository.findAll());

        return "admin/applications";
    }

    @PostMapping("/admin/applications/{id}/approve")
    public String approve(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        try {
            applicationService.approve(id);
            redirectAttributes.addFlashAttribute("success", "Application approved");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/applications";
    }

    @PostMapping("/admin/applications/{id}/reject")
    public String reject(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        try {
            applicationService.reject(id);
            redirectAttributes.addFlashAttribute("success", "Application rejected");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/applications";
    }

    @PostMapping("/applications/release/{officeId}")
    public String releaseOffice(
            @PathVariable Long officeId,
            RedirectAttributes redirectAttributes
    ) {

        try {
            applicationService.releaseOffice(officeId);
            redirectAttributes.addFlashAttribute("success", "Office released");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/applications/my";
    }

    @PostMapping("/admin/offices/release/{officeId}")
    public String adminReleaseOffice(
            @PathVariable Long officeId,
            RedirectAttributes redirectAttributes
    ) {

        try {
            applicationService.releaseOffice(officeId);
            redirectAttributes.addFlashAttribute("success", "Office released");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/applications";
    }

}