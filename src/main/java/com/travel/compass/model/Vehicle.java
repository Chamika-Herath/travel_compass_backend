package com.travel.compass.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;
    private String description;
    private String fuelType;
    private String licensePlate;
    private String location;
    private String name;
    private double rentalRate;
    private String type;



//    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<VehicleImage> images = new ArrayList<>(); // Initialize to avoid null issues
//
//
//    @ElementCollection
//    private List<String> imageUrls = new ArrayList<>();  // Keeping this as is, as per your request.

//    // NEW: Add One-to-Many Relationship with VehicleImage
//    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<VehicleImage> images; // Fetch images from a separate table

//    @ElementCollection
//    private List<String> imageUrls;  // URLs for images

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VehicleImage> vehicleImages;  // Mapping to VehicleImage


}