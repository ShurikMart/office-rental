package com.example.office_rental.dto;

import com.example.office_rental.model.OfficeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOfficeDto {

    private String number;
    private Double area;
    private Integer floor;
    private Integer capacity;
    private OfficeType officeType;
    private Boolean hasFurniture;
    private Double rentalPrice;
    private String status;
    private Long buildingId;
}