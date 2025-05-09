//package com.travel.compass.controller;
//
//
//import com.travel.compass.Dto.GuidePackageDTO;
//import com.travel.compass.service.GuidePackageService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/guide-packages")
//@CrossOrigin(origins = "http://localhost:3000") // adjust if your frontend runs elsewhere
//public class GuidePackageController {
//
//    private final GuidePackageService guidePackageService;
//
//    public GuidePackageController(GuidePackageService guidePackageService) {
//        this.guidePackageService = guidePackageService;
//    }
//
//    @PostMapping("/create/{guideId}")
//    public ResponseEntity<GuidePackageDTO> createPackage(
//            @PathVariable Long guideId,
//            @RequestBody GuidePackageDTO dto) {
//        GuidePackageDTO created = guidePackageService.createPackage(guideId, dto);
//        return ResponseEntity.ok(created);
//    }
//
//    @GetMapping("/guide/{guideId}")
//    public ResponseEntity<List<GuidePackageDTO>> getPackagesByGuide(@PathVariable Long guideId) {
//        return ResponseEntity.ok(guidePackageService.getPackagesByGuide(guideId));
//    }
//
//    @PutMapping("/update/{packageId}")
//    public ResponseEntity<GuidePackageDTO> updatePackage(
//            @PathVariable Long packageId,
//            @RequestBody GuidePackageDTO dto) {
//        return ResponseEntity.ok(guidePackageService.updatePackage(packageId, dto));
//    }
//
//    @DeleteMapping("/delete/{packageId}")
//    public ResponseEntity<String> deletePackage(@PathVariable Long packageId) {
//        guidePackageService.deletePackage(packageId);
//        return ResponseEntity.ok("Package deleted");
//    }
//}


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

    private final GuidePackageService guidePackageService;

    public GuidePackageController(GuidePackageService guidePackageService) {
        this.guidePackageService = guidePackageService;
    }

    @PostMapping(value = "/create/{guideId}", consumes = {"multipart/form-data"})
    public ResponseEntity<GuidePackageDTO> createPackage(
            @PathVariable Long guideId,
            @RequestPart("package") GuidePackageDTO dto,
            @RequestPart("images") MultipartFile[] images) throws IOException {
        GuidePackageDTO created = guidePackageService.createPackage(guideId, dto, images);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<GuidePackageDTO>> getPackagesByGuide(@PathVariable Long guideId) {
        return ResponseEntity.ok(guidePackageService.getPackagesByGuide(guideId));
    }

    @PutMapping("/update/{packageId}")
    public ResponseEntity<GuidePackageDTO> updatePackage(
            @PathVariable Long packageId,
            @RequestBody GuidePackageDTO dto) {
        return ResponseEntity.ok(guidePackageService.updatePackage(packageId, dto));
    }

    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<String> deletePackage(@PathVariable Long packageId) {
        guidePackageService.deletePackage(packageId);
        return ResponseEntity.ok("Package deleted");
    }
}
