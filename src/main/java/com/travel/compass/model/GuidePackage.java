package com.travel.compass.model;



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
    private String location;

    @ElementCollection
    private List<String> places;

    private double pricePerDay;
    private boolean available;

    @ElementCollection
    private List<String> imagePaths;


    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;
}
