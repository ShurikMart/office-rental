package com.example.office_rental.repository;

import com.example.office_rental.model.OfficeSpace;
import com.example.office_rental.model.OfficeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeSpaceRepository extends JpaRepository<OfficeSpace, Long> {

    long countByStatus(OfficeStatus status);

}