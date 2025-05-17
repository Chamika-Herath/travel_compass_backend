package com.travel.compass.Dto;

import lombok.Data;

import java.util.List;

@Data
public class LocationBasicDTO {
    private Long id;
    private String province;
    private String district;
    private String name;
    private String category;
    private List<String> imagePaths;
}
