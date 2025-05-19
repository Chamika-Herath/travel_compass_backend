package com.travel.compass.controller;

import com.travel.compass.Dto.GuidePackageDTO;
import com.travel.compass.Dto.HotelPackageDTO;
import com.travel.compass.model.HotelPackage;
import com.travel.compass.service.HotelPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel-packages")
@CrossOrigin(origins = "http://localhost:3000")
public class HotelPackageController {

    private final HotelPackageService packageService;

    public HotelPackageController(HotelPackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping(value = "/create/user/{userId}", consumes = {"multipart/form-data"})
    public ResponseEntity<HotelPackageDTO> createPackage(
            @PathVariable Long userId,
            @RequestPart("package") HotelPackageDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        return ResponseEntity.ok(packageService.createPackageByUserId(userId, dto, images));
    }

    @GetMapping("/hotel-owner/user/{userId}")
    public ResponseEntity<List<HotelPackageDTO>> getPackagesByHotelOwner(@PathVariable Long userId) {
        return ResponseEntity.ok(packageService.getPackagesByUserId(userId));
    }

    @GetMapping("/by-location/{locationId}")
    public ResponseEntity<List<HotelPackageDTO>> getPackagesByLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(packageService.getPackagesByLocation(locationId));
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<HotelPackageDTO> updatePackage(
            @PathVariable Long packageId,
            @RequestBody HotelPackageDTO dto) {
        return ResponseEntity.ok(packageService.updatePackage(packageId, dto));
    }

    @DeleteMapping("/{packageId}")
    public ResponseEntity<String> deletePackage(@PathVariable Long packageId) {
        packageService.deletePackage(packageId);
        return ResponseEntity.ok("Hotel package deleted successfully");
    }



    @GetMapping("/{packageId}")
    public ResponseEntity<HotelPackageDTO> getPackageById(@PathVariable Long packageId) {
        return ResponseEntity.ok(packageService.getPackageById(packageId));
    }
}