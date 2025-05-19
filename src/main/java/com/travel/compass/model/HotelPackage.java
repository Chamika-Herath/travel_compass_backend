package com.travel.compass.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "hotel_packages")
public class HotelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;
    private String description;
    private int bedCount;
    private double pricePerDay;
    private boolean available;

    @ElementCollection
    private List<String> imagePaths;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "hotel_owner_id")
    private HotelOwner hotelOwner;

    @ManyToMany
    @JoinTable(
            name = "hotel_package_location",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @JsonBackReference
    private List<Location> locations;
}