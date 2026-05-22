package com.example.office_rental.controller;

import com.example.office_rental.dto.CreateOfficeDto;
import com.example.office_rental.dto.FloorPlanDto;
import com.example.office_rental.model.*;
import com.example.office_rental.repository.BuildingRepository;
import com.example.office_rental.repository.FloorPlanRepository;
import com.example.office_rental.repository.OfficeSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OfficeSpaceRepository officeSpaceRepository;

    private final FloorPlanRepository floorPlanRepository;

    private final BuildingRepository buildingRepository;

    @GetMapping("/floor-editor")
    public String floorEditor(org.springframework.ui.Model model) {

        model.addAttribute(
                "offices",
                officeSpaceRepository.findAll()
        );

        model.addAttribute(
                "floorPlans",
                floorPlanRepository.findAll()
        );

        return "admin/floor-editor";
    }

    @PostMapping("/floor-plan/save")
    @ResponseBody
    public String saveFloorPlan(@RequestBody FloorPlanDto dto) {

        OfficeSpace office =
                officeSpaceRepository.findById(dto.getOfficeId())
                        .orElseThrow();

        FloorPlan floorPlan =
                floorPlanRepository
                        .findByOfficeSpaceId(dto.getOfficeId())
                        .orElse(new FloorPlan());

        floorPlan.setOfficeSpace(office);

        floorPlan.setFloorNumber(dto.getFloorNumber());

        floorPlan.setX(dto.getX());
        floorPlan.setY(dto.getY());

        floorPlan.setWidth(dto.getWidth());
        floorPlan.setHeight(dto.getHeight());

        floorPlanRepository.save(floorPlan);

        return "saved";
    }

    @PostMapping("/offices/create")
    @ResponseBody
    public OfficeSpace createOffice(
            @RequestBody CreateOfficeDto dto
    ) {

        Building building =
                buildingRepository.findAll()
                        .stream()
                        .findFirst()
                        .orElseThrow();

        OfficeSpace office = new OfficeSpace(
                dto.getNumber(),
                dto.getArea(),
                dto.getFloor(),
                1,
                "STANDARD",
                false,
                dto.getRentalPrice(),

                OfficeStatus.FREE,

                building
        );

        return officeSpaceRepository.save(office);
    }
}