package com.example.office_rental.repository;

import com.example.office_rental.model.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {

    List<FloorPlan> findByFloorNumber(Integer floorNumber);
    Optional<FloorPlan> findByOfficeSpaceId(Long officeId);

}