package com.travel.compass.controller;

import com.travel.compass.Dto.GuideDTO;
import com.travel.compass.Dto.HotelOwnerDTO;
import com.travel.compass.model.HotelOwner;
import com.travel.compass.repository.HotelOwnerRepository;
import com.travel.compass.service.GuideService;
import com.travel.compass.service.HotelOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-owners")
public class HotelOwnerController {

    private final HotelOwnerService hotelOwnerService;

    private final HotelOwnerRepository hotelOwnerRepo;

    public HotelOwnerController(HotelOwnerService hotelOwnerService, HotelOwnerRepository hotelOwnerRepo) {
        this.hotelOwnerService = hotelOwnerService;
        this.hotelOwnerRepo = hotelOwnerRepo;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<HotelOwner> getHotelOwnerByUserId(@PathVariable Long userId) {
        return hotelOwnerRepo.findByUser_Id(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<HotelOwnerDTO>> getAllHotelOwners() {
        return ResponseEntity.ok(hotelOwnerService.getAllHotelOwners());
    }

    //  Delete a ho by ID
    @DeleteMapping("/delete/{hotelOwnerId}")
    public ResponseEntity<String> deleteHotelOwner(@PathVariable Long hotelOwnerId) {
        hotelOwnerService.deleteHotel(hotelOwnerId);
        return ResponseEntity.ok("Guide deleted successfully");
    }
}