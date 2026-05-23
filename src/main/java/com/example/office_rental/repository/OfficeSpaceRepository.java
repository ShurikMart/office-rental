package com.example.office_rental.repository;

import com.example.office_rental.model.OfficeSpace;
import com.example.office_rental.model.OfficeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfficeSpaceRepository
        extends JpaRepository<OfficeSpace, Long>,
        JpaSpecificationExecutor<OfficeSpace> {

    long countByStatus(OfficeStatus status);

}