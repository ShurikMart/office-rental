package com.example.office_rental.service;

import com.example.office_rental.model.*;

import com.example.office_rental.repository.OfficeSpaceRepository;
import com.example.office_rental.repository.RentalApplicationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final RentalApplicationRepository applicationRepository;

    private final OfficeSpaceRepository officeRepository;

    private final UserService userService;

    public void createApplication(
            Long officeId,
            String comment
    ) {

        User user =
                userService.getCurrentUser();

        OfficeSpace office =
                officeRepository
                        .findById(officeId)
                        .orElseThrow();

        RentalApplication application =
                new RentalApplication(
                        user,
                        office,
                        comment
                );

        applicationRepository.save(application);
    }

    public void approve(Long applicationId) {

        RentalApplication application =
                applicationRepository
                        .findById(applicationId)
                        .orElseThrow();

        application.setStatus(
                ApplicationStatus.APPROVED
        );

        OfficeSpace office =
                application.getOfficeSpace();

        office.setStatus(
                OfficeStatus.OCCUPIED
        );

        office.setCurrentTenant(
                application.getUser()
        );

        officeRepository.save(office);

        applicationRepository.save(application);
    }

    public void reject(Long id) {

        RentalApplication app =
                applicationRepository
                        .findById(id)
                        .orElseThrow();

        app.setStatus(
                ApplicationStatus.REJECTED
        );

        applicationRepository.save(app);
    }
}