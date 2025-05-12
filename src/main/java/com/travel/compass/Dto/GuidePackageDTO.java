//package com.travel.compass.Dto;
//
//
//
//
//
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//public class GuidePackageDTO {
//    private Long id;
//    private String packageName;
//    private String location;
//    private List<String> places;
//    private double pricePerDay;
//    private boolean available;
//    private List<String> imagePaths;
//
//}


package com.travel.compass.Dto;

import lombok.Data;
import java.util.List;

@Data
public class GuidePackageDTO {
    private Long id;
    private String packageName;
    private List<Long> locationIds; // IDs of locations
    private double pricePerDay;
    private boolean available;
    private List<String> imagePaths;
}

