package com.example.office_rental.controller;

import com.example.office_rental.repository.FloorPlanRepository;
import com.example.office_rental.repository.OfficeSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeSpaceRepository officeSpaceRepository;
    private final FloorPlanRepository floorPlanRepository;

    @GetMapping("/offices")
    public String offices(Model model) {

        model.addAttribute(
                "offices",
                officeSpaceRepository.findAll()
        );

        return "offices/list";
    }

    @GetMapping("/floor-plan")
    public String floorPlan(Model model) {

        model.addAttribute(
                "floorPlans",
                floorPlanRepository.findByFloorNumber(1)
        );

        return "offices/floor-plan";
    }



}