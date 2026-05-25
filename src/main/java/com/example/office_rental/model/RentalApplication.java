package com.example.office_rental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental_applications")
@Getter
@Setter
@NoArgsConstructor
public class RentalApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "office_space_id")
    private OfficeSpace officeSpace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public RentalApplication(User user, OfficeSpace officeSpace, String comment) {

        this.user = user;
        this.officeSpace = officeSpace;

        this.comment = comment;

        this.status = ApplicationStatus.PENDING;

        this.createdAt = LocalDateTime.now();
    }
}