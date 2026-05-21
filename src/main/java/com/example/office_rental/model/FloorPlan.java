package com.example.office_rental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "floor_plans")
@Getter
@Setter
@NoArgsConstructor
public class FloorPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "office_space_id", nullable = false, unique = true)
    private OfficeSpace officeSpace;

    @Column(nullable = false)
    private Integer floorNumber;

    // Координаты и размеры для интерактивного плана
    @Column(nullable = false)
    private Double x;      // левый верхний угол X

    @Column(nullable = false)
    private Double y;      // левый верхний угол Y

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(columnDefinition = "TEXT")
    private String svgPathData; // опционально: сложная форма (полигон)

    private String color;

    public FloorPlan(OfficeSpace officeSpace, Integer floorNumber, Double x, Double y, Double width, Double height) {
        this.officeSpace = officeSpace;
        this.floorNumber = floorNumber;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}