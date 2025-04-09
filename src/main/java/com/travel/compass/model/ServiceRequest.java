package com.travel.compass.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "service_requests")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String address;
    private String nic;
    private String serviceType;
    private String description;
    private String phoneNumber;
    private String status = "PENDING";
    private String licenseNumber;
    private String businessName;
    private String vehicleTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}