package com.example.office_rental.dto;

import com.example.office_rental.model.OfficeStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeFilterDto {

    private Double minPrice;

    private Double maxPrice;

    private Double minArea;

    private Double maxArea;

    private Integer floor;

    private String officeType;

    private Boolean hasFurniture;

    private OfficeStatus status;
}