package com.example.office_rental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "office_spaces")
@Getter
@Setter
@NoArgsConstructor
public class OfficeSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String officeType;

    @Column(nullable = false)
    private Boolean hasFurniture;

    @Column(nullable = false)
    private Double rentalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfficeStatus status;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User currentTenant;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    public OfficeSpace(
            String number,
            Double area,
            Integer floor,
            Integer capacity,
            String officeType,
            Boolean hasFurniture,
            Double rentalPrice,
            OfficeStatus status,
            Building building
    ) {

        this.number = number;
        this.area = area;
        this.floor = floor;
        this.capacity = capacity;
        this.officeType = officeType;
        this.hasFurniture = hasFurniture;
        this.rentalPrice = rentalPrice;
        this.status = status;
        this.building = building;
    }

}