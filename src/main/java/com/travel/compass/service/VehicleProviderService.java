package com.travel.compass.service;

import com.travel.compass.Dto.VehicleProviderDTO;
import com.travel.compass.model.VehicleProvider;
import com.travel.compass.repository.VehicleProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class VehicleProviderService {

    private final VehicleProviderRepository vehicleProviderRepo;
    private final ModelMapper modelMapper;

    public VehicleProviderService(VehicleProviderRepository vehicleProviderRepo,
                                  ModelMapper modelMapper) {
        this.vehicleProviderRepo = vehicleProviderRepo;
        this.modelMapper = modelMapper;
    }

    public VehicleProviderDTO getByUserId(Long userId) {
        VehicleProvider provider = vehicleProviderRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Vehicle provider not found"));
        return modelMapper.map(provider, VehicleProviderDTO.class);
    }
}