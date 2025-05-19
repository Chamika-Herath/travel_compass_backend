package com.travel.compass.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;



@Data
public class HotelPackageDTO {
    private Long id;
    private String packageName;
    private double pricePerDay;
    private int bedCount;
    private List<String> imagePaths;
    private List<Long> locationIds;
    private String hotelName; // If needed
}