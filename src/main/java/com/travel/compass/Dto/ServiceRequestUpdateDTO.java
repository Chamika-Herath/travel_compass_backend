package com.travel.compass.Dto;

import lombok.Data;

@Data
public class ServiceRequestUpdateDTO {
    private String status; // Should be "APPROVED" or "REJECTED"
}

