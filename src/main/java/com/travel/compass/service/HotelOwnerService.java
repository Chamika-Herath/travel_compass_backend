package com.travel.compass.service;
import com.travel.compass.Dto.HotelOwnerDTO;
import com.travel.compass.Dto.VehicleProviderDTO;
import com.travel.compass.model.GuidePackage;
import com.travel.compass.model.HotelOwner;
import com.travel.compass.model.HotelPackage;
import com.travel.compass.model.VehicleProvider;
import com.travel.compass.repository.GuidePackageRepository;
import com.travel.compass.repository.HotelOwnerRepository;

import com.travel.compass.repository.HotelPackageRepository;
import com.travel.compass.repository.VehicleProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelOwnerService {
    private final HotelOwnerRepository hotelOwnerRepository;
    private final ModelMapper modelMapper;
    private final HotelPackageRepository hotelPackageRepository;

    public HotelOwnerService(HotelOwnerRepository hotelOwnerRepository, ModelMapper modelMapper, HotelPackageRepository hotelPackageRepository) {
        this.hotelOwnerRepository = hotelOwnerRepository;
        this.modelMapper = modelMapper;
        this.hotelPackageRepository = hotelPackageRepository;
    }





    public List<HotelOwnerDTO> getAllHotelOwners() {
        return hotelOwnerRepository.findAll().stream().map(hotelOwner -> {
            HotelOwnerDTO dto = new HotelOwnerDTO();
            dto.setId(hotelOwner.getId());
            dto.setUserId(hotelOwner.getUser().getId());
            dto.setHotelName(hotelOwner.getHotelName());
            dto.setHotelAddress(hotelOwner.getHotelAddress());
            dto.setBusinessLicense(hotelOwner.getBusinessLicense());
            dto.setStarRating(hotelOwner.getStarRating());
            dto.setAmenities(hotelOwner.getAmenities());
            return dto;
        }).collect(Collectors.toList());
    }



    //  Delete a guide and all their packages
    public void deleteHotel(Long hotelOwnerId) {
        List<HotelPackage> packages = hotelPackageRepository.findByHotelOwnerId(hotelOwnerId);
        hotelPackageRepository.deleteAll(packages);

        hotelPackageRepository.deleteById(hotelOwnerId);
    }
}
