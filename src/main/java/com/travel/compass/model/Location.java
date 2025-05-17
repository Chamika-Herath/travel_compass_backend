//
//
//
//
//
//package com.travel.compass.model;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Location {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String province;
//    private String district;
//    private String name;
//    private String category;
//
//    @Column(length = 5000)
//    private String description;
//
//    @ElementCollection
//    private List<String> imagePaths;
//
//    @ManyToMany(mappedBy = "locations")
//    @JsonManagedReference
//    private List<GuidePackage> guidePackages;
//}
//
//





package com.travel.compass.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String province;
    private String district;
    private String name;
    private String category;

    @Column(length = 5000)
    private String description;

    @ElementCollection
    private List<String> imagePaths;

    @ManyToMany(mappedBy = "locations")
    @JsonManagedReference
    private List<GuidePackage> guidePackages;

    @ManyToMany(mappedBy = "locations")
    @JsonManagedReference
    private List<HotelPackage> hotelPackages;

    @ManyToMany(mappedBy = "locations")
    @JsonManagedReference
    private List<VehiclePackage> vehiclePackages;
}