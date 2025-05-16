package com.travel.compass.controller;

import com.travel.compass.Dto.VehicleProviderDTO;
import com.travel.compass.service.VehicleProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle-providers")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleProviderController {

    private final VehicleProviderService vehicleProviderService;

    public VehicleProviderController(VehicleProviderService vehicleProviderService) {
        this.vehicleProviderService = vehicleProviderService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<VehicleProviderDTO> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(vehicleProviderService.getByUserId(userId));
    }
}