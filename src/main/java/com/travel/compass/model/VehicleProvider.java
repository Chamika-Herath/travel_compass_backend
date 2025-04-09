package com.travel.compass.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehicle_providers")
@Data
public class VehicleProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String companyName;
    private String vehicleTypes; // e.g., "Sedan,SUV,Van"
    private String licenseNumber;
    private int fleetSize;

    @Column(columnDefinition = "TEXT")
    private String serviceAreas;
}