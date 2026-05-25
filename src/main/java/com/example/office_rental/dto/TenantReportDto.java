package com.example.office_rental.dto;

import com.example.office_rental.model.RentalApplication;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TenantReportDto {

    private long activeTenants;

    private Map<String, Long> tenantOfficeCounts;

    private List<RentalApplication> rentalHistory;

}