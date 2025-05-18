//package com.travel.compass.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "users")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String firstName;
//
//    @Column(nullable = false)
//    private String lastName;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//
//    private String role = "USER"; // Default role
//
//
//    // Add these relationships if not present
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ServiceRequest> serviceRequests = new ArrayList<>();
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Guide guide;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private HotelOwner hotelOwner;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private VehicleProvider vehicleProvider;
//
//    // Helper method to manage bidirectional relationship
//    public void addServiceRequest(ServiceRequest request) {
//        serviceRequests.add(request);
//        request.setUser(this);
//    }
//
//}



package com.travel.compass.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role = "USER"; // Default role

    private String resetPasswordToken;
    private LocalDateTime resetPasswordTokenExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Guide guide;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private HotelOwner hotelOwner;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private VehicleProvider vehicleProvider;

    public void addServiceRequest(ServiceRequest request) {
        serviceRequests.add(request);
        request.setUser(this);
    }
}