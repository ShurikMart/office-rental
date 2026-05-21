package com.example.office_rental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOfficeDto {

    private String number;

    private Double area;

    private Integer floor;

    private Double rentalPrice;
}