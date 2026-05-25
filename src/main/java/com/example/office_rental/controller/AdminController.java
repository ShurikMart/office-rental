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
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OfficeSpaceRepository officeSpaceRepository;

    private final FloorPlanRepository floorPlanRepository;

    private final BuildingRepository buildingRepository;

    @GetMapping("/floor-editor")
    public String floorEditor(Model model) {

        model.addAttribute("offices", officeSpaceRepository.findAll());

        model.addAttribute("floorPlans", floorPlanRepository.findAll());

        model.addAttribute("buildings", buildingRepository.findAll());

        return "admin/floor-editor";
    }

    @PostMapping("/floor-plan/save")
    @ResponseBody
    public String saveFloorPlan(@RequestBody FloorPlanDto dto) {

        OfficeSpace office = officeSpaceRepository.findById(dto.getOfficeId()).orElseThrow();

        FloorPlan floorPlan = floorPlanRepository.findByOfficeSpaceId(dto.getOfficeId()).orElse(new FloorPlan());

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

        Building building = buildingRepository.findById(dto.getBuildingId()).orElseThrow();

        OfficeSpace office = new OfficeSpace(
                dto.getNumber(),
                dto.getArea(),
                dto.getFloor(),
                dto.getCapacity(),
                dto.getOfficeType(),
                dto.getHasFurniture(),
                dto.getRentalPrice(),
                OfficeStatus.valueOf(dto.getStatus()),
                building
        );

        return officeSpaceRepository.save(office);
    }

    @GetMapping("/offices/{id}")
    @ResponseBody
    public OfficeSpace getOffice(@PathVariable Long id) {
        return officeSpaceRepository.findById(id).orElseThrow();
    }

    @PutMapping("/offices/update/{id}")
    @ResponseBody
    public OfficeSpace updateOffice(@PathVariable Long id, @RequestBody CreateOfficeDto dto) {

        OfficeSpace office = officeSpaceRepository.findById(id).orElseThrow();

        office.setNumber(dto.getNumber());
        office.setArea(dto.getArea());
        office.setFloor(dto.getFloor());
        office.setCapacity(dto.getCapacity());
        office.setOfficeType(dto.getOfficeType());
        office.setHasFurniture(dto.getHasFurniture());
        office.setRentalPrice(dto.getRentalPrice());
        office.setStatus(dto.getStatus() != null ? OfficeStatus.valueOf(dto.getStatus()) : office.getStatus());

        return officeSpaceRepository.save(office);
    }

    @DeleteMapping("/offices/delete/{id}")
    @ResponseBody
    public String deleteOffice(@PathVariable Long id) {
        floorPlanRepository.findByOfficeSpaceId(id).ifPresent(floorPlanRepository::delete);
        officeSpaceRepository.deleteById(id);
        return "deleted";
    }

}