//package com.travel.compass.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.Data;
//import java.util.List;
//
//@Entity
//@Data
//public class VehiclePackage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String vehicleModel;
//
//    @Enumerated(EnumType.STRING)
//    private VehicleType vehicleType; // enum: BIKE, CAR, VAN, THREE_WHEELER
//
//    private String licensePlate;
//    private String description;
//    private int passengerCapacity;
//
//    @Enumerated(EnumType.STRING)
//    private FuelType fuelType; // enum: PETROL, DIESEL, etc.
//
//    private double pricePerDay;
//    private double pricePerKm;
//
//    @ElementCollection
//    private List<String> imagePaths;
//
//    @ManyToOne
//    @JoinColumn(name = "vehicle_provider_id")
//    private VehicleProvider vehicleProvider;
//
//    @ManyToMany
//    @JoinTable(
//            name = "vehicle_package_location",
//            joinColumns = @JoinColumn(name = "package_id"),
//            inverseJoinColumns = @JoinColumn(name = "location_id")
//    )
//    @JsonBackReference
//    private List<Location> locations;
//}




package com.travel.compass.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class VehiclePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleModel;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private String licensePlate;
    private String description;
    private int passengerCapacity;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    private double pricePerDay;
    private double pricePerKm;

    @ElementCollection
    private List<String> imagePaths;

    @ManyToOne
    @JoinColumn(name = "vehicle_provider_id")
    private VehicleProvider vehicleProvider;

    @ManyToMany
    @JoinTable(
            name = "vehicle_package_location",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @JsonBackReference
    private List<Location> locations;
}