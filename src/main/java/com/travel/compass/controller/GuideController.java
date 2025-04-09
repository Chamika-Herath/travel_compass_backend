//package com.travel.compass.controller;
//
//import com.travel.compass.model.Guide;
//import com.travel.compass.repository.GuideRepository;
//import com.travel.compass.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/guides")
//@RequiredArgsConstructor
//public class GuideController {
//    private final GuideRepository guideRepository;
//    private final UserRepository userRepository;
//
//    @GetMapping
//    public List<Guide> getAllGuides() {
//        return guideRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Guide> getGuide(@PathVariable Long id) {
//        return ResponseEntity.ok(guideRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Guide not found")));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<Guide> getGuideByUserId(@PathVariable Long userId) {
//        return ResponseEntity.ok(guideRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("Guide not found for user")));
//    }
//}