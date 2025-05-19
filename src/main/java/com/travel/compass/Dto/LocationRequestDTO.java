package com.travel.compass.Dto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDTO {
    private String province;
    private String district;
    private String name;
    private String category;
    private String description;
    private List<MultipartFile> images;
}

