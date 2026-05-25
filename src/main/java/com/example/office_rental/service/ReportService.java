package com.example.office_rental.service;

import com.example.office_rental.dto.ApplicationReportDto;
import com.example.office_rental.dto.OfficeReportDto;
import com.example.office_rental.dto.TenantReportDto;
import com.example.office_rental.model.ApplicationStatus;
import com.example.office_rental.model.OfficeSpace;
import com.example.office_rental.model.OfficeStatus;
import com.example.office_rental.model.RentalApplication;
import com.example.office_rental.repository.OfficeSpaceRepository;
import com.example.office_rental.repository.RentalApplicationRepository;
import com.example.office_rental.repository.UserRepository;

import java.util.Comparator;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OfficeSpaceRepository officeRepository;

    private final RentalApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    public OfficeReportDto getOfficeReport() {

        List<OfficeSpace> offices = officeRepository.findAll();

        OfficeReportDto dto = new OfficeReportDto();

        dto.setTotalOffices(offices.size());

        dto.setFreeOffices(officeRepository.countByStatus(OfficeStatus.FREE));

        dto.setOccupiedOffices(officeRepository.countByStatus(OfficeStatus.OCCUPIED));

        dto.setAveragePrice(offices.stream().mapToDouble(OfficeSpace::getRentalPrice).average().orElse(0));

        dto.setAverageArea(offices.stream().mapToDouble(OfficeSpace::getArea).average().orElse(0));

        dto.setOfficesByType(offices.stream().collect(Collectors.groupingBy(office -> office.getOfficeType().name(), Collectors.counting())));

        return dto;
    }

    public ApplicationReportDto getApplicationReport() {

        List<RentalApplication> applications = applicationRepository.findAll();

        ApplicationReportDto dto = new ApplicationReportDto();

        dto.setTotalApplications(applications.size());

        dto.setPendingApplications(applicationRepository.countByStatus(ApplicationStatus.PENDING));

        dto.setApprovedApplications(applicationRepository.countByStatus(ApplicationStatus.APPROVED));

        dto.setRejectedApplications(applicationRepository.countByStatus(ApplicationStatus.REJECTED));

        double approvalRate = 0;
        if (!applications.isEmpty()) {
            approvalRate = (double) dto.getApprovedApplications() / applications.size() * 100;
        }

        dto.setApprovalRate(Math.round(approvalRate * 100.0) / 100.0);

        dto.setMostActiveUser(applications.stream().collect(
                        Collectors.groupingBy(app -> app.getUser().getUsername(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(java.util.Map.Entry.comparingByValue()
                        ).map(java.util.Map.Entry::getKey).orElse("Нет данных")
        );

        dto.setMostRequestedOffice(applications.stream().collect(
                        Collectors.groupingBy(app -> app.getOfficeSpace().getNumber(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(java.util.Map.Entry.comparingByValue())
                        .map(java.util.Map.Entry::getKey).orElse("Нет данных")
        );

        return dto;
    }
    public TenantReportDto getTenantReport() {

        List<RentalApplication> applications = applicationRepository.findAll();

        TenantReportDto dto = new TenantReportDto();

        long activeTenants = applications.stream().filter(app -> app.getStatus() == ApplicationStatus.APPROVED)
                        .map(app -> app.getUser().getId()).distinct().count();

        dto.setActiveTenants(activeTenants);

        Map<String, Long> tenantOfficeCounts = applications.stream().filter(app -> app.getStatus() == ApplicationStatus.APPROVED)
                        .collect(Collectors.groupingBy(app -> app.getUser().getUsername(), Collectors.counting()));

        dto.setTenantOfficeCounts(tenantOfficeCounts);

        List<RentalApplication> rentalHistory = applications.stream()
                        .sorted(Comparator.comparing(RentalApplication::getCreatedAt).reversed()).toList();

        dto.setRentalHistory(rentalHistory);

        return dto;
    }

}