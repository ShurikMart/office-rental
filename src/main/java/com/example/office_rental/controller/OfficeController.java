package com.example.office_rental.controller;

import com.example.office_rental.model.OfficeSpace;
import com.example.office_rental.model.OfficeStatus;
import com.example.office_rental.repository.FloorPlanRepository;
import com.example.office_rental.repository.OfficeSpaceRepository;
import com.example.office_rental.specification.OfficeSpecification;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeSpaceRepository officeSpaceRepository;

    private final FloorPlanRepository floorPlanRepository;

    @GetMapping("/offices")
    public String offices(

            @RequestParam(required = false)
            OfficeStatus status,

            @RequestParam(required = false)
            Double minPrice,

            @RequestParam(required = false)
            Double maxPrice,

            @RequestParam(required = false)
            Integer floor,

            @RequestParam(required = false)
            Double minArea,

            @RequestParam(required = false)
            Double maxArea,

            @RequestParam(required = false)
            Integer minCapacity,

            @RequestParam(required = false)
            String officeType,

            @RequestParam(required = false)
            Boolean hasFurniture,

            Model model
    ) {

        List<OfficeSpace> offices =
                getFilteredOffices(
                        status,
                        minPrice,
                        maxPrice,
                        floor,
                        minArea,
                        maxArea,
                        minCapacity,
                        officeType,
                        hasFurniture
                );

        model.addAttribute("offices", offices);

        return "offices/list";
    }

    @GetMapping("/floor-plan")
    public String floorPlan(

            @RequestParam(required = false)
            OfficeStatus status,

            @RequestParam(required = false)
            Double minPrice,

            @RequestParam(required = false)
            Double maxPrice,

            @RequestParam(required = false)
            Integer floor,

            @RequestParam(required = false)
            Double minArea,

            @RequestParam(required = false)
            Double maxArea,

            @RequestParam(required = false)
            Integer minCapacity,

            @RequestParam(required = false)
            String officeType,

            @RequestParam(required = false)
            Boolean hasFurniture,

            Model model,

            Authentication authentication
    ) {

        List<OfficeSpace> offices =
                getFilteredOffices(
                        status,
                        minPrice,
                        maxPrice,
                        floor,
                        minArea,
                        maxArea,
                        minCapacity,
                        officeType,
                        hasFurniture
                );

        model.addAttribute("floorPlans", floorPlanRepository.findByOfficeSpaceIn(offices));

        boolean isAdmin =
                authentication != null && authentication.getAuthorities().stream().anyMatch(
                                        a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser =
                authentication != null && authentication.getAuthorities().stream().anyMatch(
                                        a -> a.getAuthority().equals("ROLE_USER"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);

        return "offices/floor-plan";
    }

    private List<OfficeSpace> getFilteredOffices(

            OfficeStatus status,
            Double minPrice,
            Double maxPrice,
            Integer floor,
            Double minArea,
            Double maxArea,
            Integer minCapacity,
            String officeType,
            Boolean hasFurniture
    ) {

        Specification<OfficeSpace> spec =
                OfficeSpecification.hasStatus(status)
                        .and(OfficeSpecification.minPrice(minPrice))

                        .and(OfficeSpecification.maxPrice(maxPrice))

                        .and(OfficeSpecification.floor(floor))

                        .and(OfficeSpecification.minArea(minArea))

                        .and(OfficeSpecification.maxArea(maxArea))

                        .and(OfficeSpecification.minCapacity(minCapacity))

                        .and(OfficeSpecification.officeType(officeType))

                        .and(OfficeSpecification.hasFurniture(hasFurniture));

        return officeSpaceRepository.findAll(spec);
    }

}