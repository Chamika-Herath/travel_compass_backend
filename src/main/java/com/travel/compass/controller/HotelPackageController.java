package com.travel.compass.controller;

import com.travel.compass.model.HotelPackage;
import com.travel.compass.service.HotelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000") // Adjust if frontend runs on a different port
@RestController
@RequestMapping("/api/packages")
public class HotelPackageController {

    @Autowired
    private HotelPackageService hotelPackageService;

    private final HotelPackageService service;

    public HotelPackageController(HotelPackageService service) {
        this.service = service;
    }

    @GetMapping
    public List<HotelPackage> getAllPackages() {
        return service.getAllPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelPackage> getPackageById(@PathVariable Long id) {
        Optional<HotelPackage> hotelPackage = service.getPackageById(id);
        return hotelPackage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HotelPackage> createPackage(@RequestBody HotelPackage hotelPackage) {
        HotelPackage newPackage = hotelPackageService.createPackage(hotelPackage);
        return ResponseEntity.ok(newPackage);
    }
    @PutMapping("/{id}")
    public ResponseEntity<HotelPackage> updatePackage(@PathVariable Long id, @RequestBody HotelPackage updatedPackage) {
        try {
            return ResponseEntity.ok(service.updatePackage(id, updatedPackage));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable Long id) {
        if (!service.getPackageById(id).isPresent()) {
            return ResponseEntity.status(404).body("Package not found");
        }

        try {
            service.deletePackage(id);
            return ResponseEntity.ok("Package deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting package: " + e.getMessage());
        }
    }
}
