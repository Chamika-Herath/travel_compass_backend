package com.travel.compass.controller;

import com.travel.compass.model.HotelOwner;
import com.travel.compass.repository.HotelOwnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel-owners")
public class HotelOwnerController {

    private final HotelOwnerRepository hotelOwnerRepo;

    public HotelOwnerController(HotelOwnerRepository hotelOwnerRepo) {
        this.hotelOwnerRepo = hotelOwnerRepo;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<HotelOwner> getHotelOwnerByUserId(@PathVariable Long userId) {
        return hotelOwnerRepo.findByUser_Id(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}