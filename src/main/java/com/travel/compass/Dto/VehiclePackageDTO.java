// VehiclePackageDTO.java (new)
package com.travel.compass.Dto;

import lombok.Data;
import java.util.List;

//@Data
//public class VehiclePackageDTO {
//    private Long id;
//    private String vehicleModel;
//    private String vehicleType;
//    private String licensePlate;
//    private String description;
//    private int passengerCapacity;
//    private String fuelType;
//    private double pricePerDay;
//    private double pricePerKm;
//    private List<Long> locationIds;
//    private List<String> imagePaths;
//}



@Data
public class VehiclePackageDTO {
    private Long id;
    private String vehicleModel;
    private String vehicleType;
    private double pricePerDay;
    private double pricePerKm;
    private List<String> imagePaths;
    private List<Long> locationIds;
    private String providerName;
    private String fuelType;// If needed
}