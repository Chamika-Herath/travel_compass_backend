package com.travel.compass.controller;

import com.travel.compass.Dto.HotelPackageDTO;
import com.travel.compass.Dto.VehiclePackageDTO;
import com.travel.compass.service.VehiclePackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle-packages")
@CrossOrigin(origins = "http://localhost:3000")
public class VehiclePackageController {

    private final VehiclePackageService vehiclePackageService;

    public VehiclePackageController(VehiclePackageService vehiclePackageService) {
        this.vehiclePackageService = vehiclePackageService;
    }

    @PostMapping(value = "/create/user/{userId}", consumes = {"multipart/form-data"})
    public ResponseEntity<VehiclePackageDTO> createVehiclePackage(
            @PathVariable Long userId,
            @RequestPart("package") VehiclePackageDTO dto,
            @RequestPart("images") MultipartFile[] images) throws IOException {
        return ResponseEntity.ok(vehiclePackageService.createPackage(userId, dto, images));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehiclePackageDTO>> getPackagesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(vehiclePackageService.getPackagesByUserId(userId));
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<VehiclePackageDTO>> getPackagesByLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(vehiclePackageService.getPackagesByLocationId(locationId));
    }

    @PutMapping(value = "/{packageId}", consumes = {"multipart/form-data"})
    public ResponseEntity<VehiclePackageDTO> updatePackage(
            @PathVariable Long packageId,
            @RequestPart("package") VehiclePackageDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        return ResponseEntity.ok(vehiclePackageService.updatePackage(packageId, dto, images));
    }

    @DeleteMapping("/{packageId}")
    public ResponseEntity<String> deletePackage(@PathVariable Long packageId) {
        vehiclePackageService.deletePackage(packageId);
        return ResponseEntity.ok("Vehicle package deleted successfully");
    }


    @GetMapping("/{packageId}")
    public ResponseEntity<VehiclePackageDTO> getPackageById(@PathVariable Long packageId) {
        return ResponseEntity.ok(vehiclePackageService.getPackageById(packageId));
    }
}