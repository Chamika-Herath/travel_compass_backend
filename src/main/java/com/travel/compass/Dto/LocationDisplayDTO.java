package com.travel.compass.Dto;

import lombok.Data;
import java.util.List;

@Data
public class LocationDisplayDTO {
    private Long id;
    private String province;
    private String district;
    private String name;
    private String category;
    private String description;
    private List<String> imagePaths;
    private List<GuidePackageDTO> guidePackages;
    private List<HotelPackageDTO> hotelPackages;
    private List<VehiclePackageDTO> vehiclePackages;
}