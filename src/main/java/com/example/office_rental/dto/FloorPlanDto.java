package com.example.office_rental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorPlanDto {

    private Long officeId;

    private Integer floorNumber;

    private Double x;

    private Double y;

    private Double width;

    private Double height;

}