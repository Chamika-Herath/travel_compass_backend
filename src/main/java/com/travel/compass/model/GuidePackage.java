package com.travel.compass.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class GuidePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;
    private double pricePerDay;
    private boolean available;

    @ElementCollection
    private List<String> imagePaths;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @ManyToMany
    @JoinTable(
            name = "guide_package_location",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @JsonBackReference
    private List<Location> locations;
}
