package com.travel.compass.Dto;

import lombok.Data;

@Data
public class VehicleProviderDTO {
    private Long id;
    private Long userId;
    private String companyName;
    private String vehicleTypes;
    private String licenseNumber;
    private int fleetSize;
    private String serviceAreas;
}