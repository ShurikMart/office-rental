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

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OfficeStatus status;

}