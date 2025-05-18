package com.travel.compass.Dto;

import lombok.Data;

@Data

public class HotelOwnerDTO {
    private Long id;
    private Long userId;
    private String hotelName;
    private String hotelAddress;
    private String businessLicense;
    private int starRating;
    private String amenities;
}
