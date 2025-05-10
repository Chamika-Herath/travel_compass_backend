package com.travel.compass.Dto;

import lombok.Data;

@Data
public class GuideDTO {
    private Long id;
    private Long userId;
    private String licenseNumber;
    private String areasOfExpertise;
    private String languagesSpoken;
    private String description;
    private boolean verified;
}
