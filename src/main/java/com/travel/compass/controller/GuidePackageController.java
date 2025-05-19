package com.travel.compass.controller;

import com.travel.compass.Dto.GuidePackageDTO;
import com.travel.compass.service.GuidePackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/guide-packages")
@CrossOrigin(origins = "http://localhost:3000")
public class GuidePackageController {

    private final GuidePackageService packageService;

    public GuidePackageController(GuidePackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping(value = "/create/user/{userId}", consumes = {"multipart/form-data"})
    public ResponseEntity<GuidePackageDTO> createPackage(
            @PathVariable Long userId,
            @RequestPart("package") GuidePackageDTO dto,
            @RequestPart("images") MultipartFile[] images) throws IOException {
        return ResponseEntity.ok(packageService.createPackageByUserId(userId, dto, images));
    }

    @GetMapping("/guide/user/{userId}")
    public ResponseEntity<List<GuidePackageDTO>> getPackagesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(packageService.getPackagesByUserId(userId));
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<GuidePackageDTO> updatePackage(
            @PathVariable Long packageId,
            @RequestBody GuidePackageDTO dto) {
        return ResponseEntity.ok(packageService.updatePackage(packageId, dto));
    }

    @DeleteMapping("/{packageId}")
    public ResponseEntity<String> deletePackage(@PathVariable Long packageId) {
        packageService.deletePackage(packageId);
        return ResponseEntity.ok("Package deleted");
    }


    @GetMapping("/{packageId}")
    public ResponseEntity<GuidePackageDTO> getPackageById(@PathVariable Long packageId) {
        return ResponseEntity.ok(packageService.getPackageById(packageId));
    }
}