package com.travel.compass.model;

import com.travel.compass.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String status = "PENDING"; // Default status

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key linking to User
    private User user;
}
