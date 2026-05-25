package com.example.office_rental.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OfficeReportDto {

    private long totalOffices;

    private long freeOffices;

    private long occupiedOffices;

    private double averagePrice;

    private double averageArea;

    private Map<String, Long> officesByType;

}