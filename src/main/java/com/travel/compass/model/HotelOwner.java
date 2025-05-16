//package com.travel.compass.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "hotel_owners")
//@Data
//public class HotelOwner {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    private String hotelName;
//    private String hotelAddress;
//    private String businessLicense;
//    private int starRating;
//
//    @Column(columnDefinition = "TEXT")
//    private String amenities;
//}




package com.travel.compass.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel_owners")
@Data
public class HotelOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String hotelName;
    private String hotelAddress;
    private String businessLicense;
    private int starRating;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    @OneToMany(mappedBy = "hotelOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelPackage> packages = new ArrayList<>();
}