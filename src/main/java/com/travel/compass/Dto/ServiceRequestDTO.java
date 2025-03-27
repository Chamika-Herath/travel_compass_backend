package com.travel.compass.Dto;
import lombok.Data;

@Data
public class ServiceRequestDTO {
    private Long userId;  // Include user ID
    private String fullName;
    private String address;
    private String nic;
    private String serviceType;
    private String description;
    private String phoneNumber;
}
