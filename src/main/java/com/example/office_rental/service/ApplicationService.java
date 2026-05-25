package com.example.office_rental.service;

import com.example.office_rental.model.*;
import com.example.office_rental.repository.OfficeSpaceRepository;
import com.example.office_rental.repository.RentalApplicationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final RentalApplicationRepository applicationRepository;

    private final OfficeSpaceRepository officeRepository;

    private final UserService userService;

    @Transactional
    public void createApplication(Long officeId, String comment) {

        User user = userService.getCurrentUser();

        OfficeSpace office = officeRepository.findById(officeId).orElseThrow(() -> new RuntimeException("Office not found"));


        if (office.getStatus() == OfficeStatus.OCCUPIED) {
            throw new RuntimeException("Office already occupied");
        }


        boolean alreadyExists = applicationRepository.existsByUserAndOfficeSpaceAndStatus(user, office, ApplicationStatus.PENDING);

        if (alreadyExists) {
            throw new RuntimeException("Application already exists");
        }

        RentalApplication application = new RentalApplication(user, office, comment);

        application.setStatus(ApplicationStatus.PENDING);

        applicationRepository.save(application);
    }

    @Transactional
    public void approve(Long applicationId) {

        RentalApplication application = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found"));


        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new RuntimeException("Application already processed");
        }

        OfficeSpace office = application.getOfficeSpace();


        if (office.getStatus() == OfficeStatus.OCCUPIED) {
            throw new RuntimeException("Office already occupied");
        }

        application.setStatus(ApplicationStatus.APPROVED);

        office.setStatus(OfficeStatus.OCCUPIED);

        office.setCurrentTenant(application.getUser());

        officeRepository.save(office);


        List<RentalApplication> applications = applicationRepository.findByOfficeSpace(office);

        for (RentalApplication app : applications) {

            if (!app.getId().equals(application.getId()) && app.getStatus() == ApplicationStatus.PENDING) {

                app.setStatus(ApplicationStatus.REJECTED);
                applicationRepository.save(app);
            }
        }

        applicationRepository.save(application);
    }

    @Transactional
    public void reject(Long id) {

        RentalApplication application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));


        if (application.getStatus() != ApplicationStatus.PENDING) {

            throw new RuntimeException(
                    "Application already processed"
            );
        }

        application.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);
    }

    @Transactional
    public void releaseOffice(Long officeId) {

        User currentUser = userService.getCurrentUser();

        OfficeSpace office = officeRepository.findById(officeId).orElseThrow();

        boolean isAdmin = currentUser.getRole().name().equals("ADMIN");
        boolean isTenant = office.getCurrentTenant() != null && office.getCurrentTenant().getId().equals(currentUser.getId());

        if (!isAdmin && !isTenant) {

            throw new RuntimeException("You cannot release this office");
        }

        if (office.getStatus() == OfficeStatus.FREE) {

            throw new RuntimeException("Office already free");
        }

        office.setStatus(OfficeStatus.FREE);

        office.setCurrentTenant(null);

        officeRepository.save(office);
    }

}