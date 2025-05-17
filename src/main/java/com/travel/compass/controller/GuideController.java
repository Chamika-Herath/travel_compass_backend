package com.travel.compass.controller;

import com.travel.compass.Dto.GuideDTO;
import com.travel.compass.service.GuideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guides")
@CrossOrigin(origins = "http://localhost:3000")
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    // ✅ Get all guides (as DTOs to avoid repetition or circular references)
    @GetMapping("/all")
    public ResponseEntity<List<GuideDTO>> getAllGuides() {
        return ResponseEntity.ok(guideService.getAllGuides());
    }

    // ✅ Delete a guide by ID
   // @DeleteMapping("/delete/{guideId}")
   // public ResponseEntity<String> deleteGuide(@PathVariable Long guideId) {
    //    guideService.deleteGuide(guideId);
     //   return ResponseEntity.ok("Guide deleted successfully");
   // }
}
