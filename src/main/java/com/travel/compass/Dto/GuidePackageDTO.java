package com.travel.compass.Dto;

import lombok.Data;
import java.util.List;



@Data
public class GuidePackageDTO {
    private Long id;
    private String packageName;
    private double pricePerDay;
    private boolean available;
    private List<String> imagePaths;
    private List<Long> locationIds; // For showing associated locations
    private String guideName; // If needed
}
