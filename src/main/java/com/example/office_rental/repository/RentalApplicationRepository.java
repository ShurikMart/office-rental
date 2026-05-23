package com.example.office_rental.repository;

import com.example.office_rental.model.ApplicationStatus;
import com.example.office_rental.model.OfficeSpace;
import com.example.office_rental.model.RentalApplication;
import com.example.office_rental.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalApplicationRepository
        extends JpaRepository<RentalApplication, Long> {

    List<RentalApplication> findByUser(User user);

    long countByStatus(ApplicationStatus status);

    boolean existsByUserAndOfficeSpaceAndStatus(
            User user,
            OfficeSpace officeSpace,
            ApplicationStatus status
    );

    List<RentalApplication> findByOfficeSpace(
            OfficeSpace officeSpace
    );
}