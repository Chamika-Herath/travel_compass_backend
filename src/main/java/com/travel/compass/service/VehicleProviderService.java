package com.travel.compass.service;


import com.travel.compass.Dto.VehicleProviderDTO;
import com.travel.compass.model.VehiclePackage;
import com.travel.compass.model.VehicleProvider;
import com.travel.compass.repository.VehiclePackageRepository;
import com.travel.compass.repository.VehicleProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleProviderService {

    private final VehicleProviderRepository vehicleProviderRepo;
    private final ModelMapper modelMapper;
    private final VehiclePackageRepository vehiclePackageRepository;

    public VehicleProviderService(VehicleProviderRepository vehicleProviderRepo,
                                  ModelMapper modelMapper, VehiclePackageRepository vehiclePackageRepository) {
        this.vehicleProviderRepo = vehicleProviderRepo;
        this.modelMapper = modelMapper;
        this.vehiclePackageRepository = vehiclePackageRepository;
    }

    public VehicleProviderDTO getByUserId(Long userId) {
        VehicleProvider provider = vehicleProviderRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Vehicle provider not found"));
        return modelMapper.map(provider, VehicleProviderDTO.class);
    }



    public List<VehicleProviderDTO> getAllVehicleProvider() {
        return vehicleProviderRepo.findAll().stream().map(vehicleProvider -> {
            VehicleProviderDTO dto = new VehicleProviderDTO();
            dto.setId(vehicleProvider.getId());
            dto.setUserId(vehicleProvider.getUser().getId());
            dto.setLicenseNumber(vehicleProvider.getLicenseNumber());
            dto.setVehicleTypes(vehicleProvider.getVehicleTypes());
            dto.setFleetSize(vehicleProvider.getFleetSize());
            dto.setServiceAreas(vehicleProvider.getServiceAreas());
            dto.setCompanyName(vehicleProvider.getCompanyName());
            return dto;
        }).collect(Collectors.toList());
    }




    //  Delete a guide and all their packages
    public void deleteVehicle(Long vehicleProviderId) {
        List<VehiclePackage> packages = vehiclePackageRepository.findByVehicleProviderId(vehicleProviderId);
        vehiclePackageRepository.deleteAll(packages);

        vehiclePackageRepository.deleteById(vehicleProviderId);
    }
}