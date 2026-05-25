package com.example.office_rental.controller;

import com.example.office_rental.service.ReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/admin/reports")
    public String reports(Model model) {

        model.addAttribute("officeReport", reportService.getOfficeReport());
        model.addAttribute("applicationReport", reportService.getApplicationReport());
        model.addAttribute("tenantReport", reportService.getTenantReport());

        return "admin/reports";
    }


}