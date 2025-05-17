//// VehiclePackageService.java (new)
//package com.travel.compass.service;
//
//import com.travel.compass.Dto.VehiclePackageDTO;
//import com.travel.compass.model.*;
//import com.travel.compass.repository.*;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class VehiclePackageService {
//    private final VehiclePackageRepository packageRepo;
//    private final VehicleProviderRepository providerRepo;
//    private final LocationRepository locationRepo;
//    private final ModelMapper modelMapper;
//    private final AuthorizationService authService;
//
//    public VehiclePackageService(VehiclePackageRepository packageRepo,
//                                 VehicleProviderRepository providerRepo,
//                                 LocationRepository locationRepo,
//                                 ModelMapper modelMapper,
//                                 AuthorizationService authService) {
//        this.packageRepo = packageRepo;
//        this.providerRepo = providerRepo;
//        this.locationRepo = locationRepo;
//        this.modelMapper = modelMapper;
//        this.authService = authService;
//    }
//
//    public VehiclePackageDTO createPackage(Long userId, VehiclePackageDTO dto, MultipartFile[] images) throws IOException {
//        authService.validateUserRole(userId, "ROLE_VEHICLE_OWNER");
//
//        VehicleProvider provider = providerRepo.findByUser_Id(userId)
//                .orElseThrow(() -> new RuntimeException("Vehicle provider not found"));
//
//        VehiclePackage vehiclePackage = modelMapper.map(dto, VehiclePackage.class);
//        vehiclePackage.setVehicleType(VehicleType.valueOf(dto.getVehicleType()));
//        vehiclePackage.setFuelType(FuelType.valueOf(dto.getFuelType()));
//        vehiclePackage.setVehicleProvider(provider);
//
//        // Handle locations
//        vehiclePackage.setLocations(dto.getLocationIds().stream()
//                .map(id -> locationRepo.findById(id)
//                        .orElseThrow(() -> new RuntimeException("Location not found")))
//                .collect(Collectors.toList()));
//
//        // Handle images (max 3)
//        List<String> imagePaths = new ArrayList<>();
//        if(images != null && images.length > 0) {
//            if(images.length > 3) throw new RuntimeException("Maximum 3 images allowed");
//
//            String uploadDir = "uploads/vehicle-packages/";
//            Files.createDirectories(Paths.get(uploadDir));
//
//            for(MultipartFile file : Arrays.copyOfRange(images, 0, Math.min(images.length, 3))) {
//                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
//                Path path = Paths.get(uploadDir + filename);
//                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//                imagePaths.add("/" + uploadDir + filename);
//            }
//        }
//        vehiclePackage.setImagePaths(imagePaths);
//
//        return modelMapper.map(packageRepo.save(vehiclePackage), VehiclePackageDTO.class);
//    }
//
//    // Add other CRUD methods similar to GuidePackageService
//}



package com.travel.compass.service;

import com.travel.compass.Dto.HotelPackageDTO;
import com.travel.compass.Dto.VehiclePackageDTO;
import com.travel.compass.model.*;
import com.travel.compass.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehiclePackageService {

    private final VehiclePackageRepository vehiclePackageRepo;
    private final VehicleProviderRepository vehicleProviderRepo;
    private final LocationRepository locationRepo;
    private final ModelMapper modelMapper;
    private final AuthorizationService authService;

    public VehiclePackageService(VehiclePackageRepository vehiclePackageRepo,
                                 VehicleProviderRepository vehicleProviderRepo,
                                 LocationRepository locationRepo,
                                 ModelMapper modelMapper,
                                 AuthorizationService authService) {
        this.vehiclePackageRepo = vehiclePackageRepo;
        this.vehicleProviderRepo = vehicleProviderRepo;
        this.locationRepo = locationRepo;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    public VehiclePackageDTO createPackage(Long userId, VehiclePackageDTO dto, MultipartFile[] images) throws IOException {
        authService.validateUserRole(userId, "ROLE_VEHICLE_OWNER");

        VehicleProvider provider = vehicleProviderRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Vehicle provider not found for user ID: " + userId));

        VehiclePackage vehiclePackage = modelMapper.map(dto, VehiclePackage.class);
        vehiclePackage.setVehicleType(VehicleType.valueOf(dto.getVehicleType()));
        vehiclePackage.setFuelType(FuelType.valueOf(dto.getFuelType()));
        vehiclePackage.setVehicleProvider(provider);

        // Handle locations
        vehiclePackage.setLocations(dto.getLocationIds().stream()
                .map(id -> locationRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id)))
                .collect(Collectors.toList()));

        // Handle images
        List<String> imagePaths = processImages(images);
        vehiclePackage.setImagePaths(imagePaths);

        return modelMapper.map(vehiclePackageRepo.save(vehiclePackage), VehiclePackageDTO.class);
    }

    public List<VehiclePackageDTO> getPackagesByUserId(Long userId) {
        VehicleProvider provider = vehicleProviderRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Vehicle provider not found"));
        return vehiclePackageRepo.findByVehicleProviderId(provider.getId()).stream()
                .map(pkg -> modelMapper.map(pkg, VehiclePackageDTO.class))
                .collect(Collectors.toList());
    }

    public List<VehiclePackageDTO> getPackagesByLocationId(Long locationId) {
        return vehiclePackageRepo.findByLocationId(locationId).stream()
                .map(pkg -> modelMapper.map(pkg, VehiclePackageDTO.class))
                .collect(Collectors.toList());
    }

    public VehiclePackageDTO updatePackage(Long packageId, VehiclePackageDTO dto, MultipartFile[] images) throws IOException {
        VehiclePackage existingPackage = vehiclePackageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Vehicle package not found"));

        // Update fields
        modelMapper.map(dto, existingPackage);
        existingPackage.setVehicleType(VehicleType.valueOf(dto.getVehicleType()));
        existingPackage.setFuelType(FuelType.valueOf(dto.getFuelType()));

        // Update locations
        existingPackage.setLocations(dto.getLocationIds().stream()
                .map(id -> locationRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Location not found")))
                .collect(Collectors.toList()));

        // Update images if provided
        if(images != null && images.length > 0) {
            List<String> newImagePaths = processImages(images);
            existingPackage.setImagePaths(newImagePaths);
        }

        return modelMapper.map(vehiclePackageRepo.save(existingPackage), VehiclePackageDTO.class);
    }

    public void deletePackage(Long packageId) {
        VehiclePackage vehiclePackage = vehiclePackageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Vehicle package not found"));

        // Delete associated images
        if(vehiclePackage.getImagePaths() != null) {
            vehiclePackage.getImagePaths().forEach(path -> {
                try {
                    Files.deleteIfExists(Paths.get(path.startsWith("/") ? path.substring(1) : path));
                } catch (IOException e) {
                    System.err.println("Failed to delete image: " + path);
                }
            });
        }

        vehiclePackageRepo.delete(vehiclePackage);
    }

    private List<String> processImages(MultipartFile[] images) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        if(images != null && images.length > 0) {
            if(images.length > 3) throw new RuntimeException("Maximum 3 images allowed");

            String uploadDir = "uploads/vehicle-packages/";
            Files.createDirectories(Paths.get(uploadDir));

            for(MultipartFile file : Arrays.copyOfRange(images, 0, Math.min(images.length, 3))) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + filename);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                imagePaths.add("/" + uploadDir + filename);
            }
        }
        return imagePaths;
    }




    public VehiclePackageDTO getPackageById(Long packageId) {
        VehiclePackage vehiclePackage = vehiclePackageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        VehiclePackageDTO dto = modelMapper.map(vehiclePackage, VehiclePackageDTO.class);

        // Set location IDs
        dto.setLocationIds(vehiclePackage.getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));

        // Set guide name using User's firstName + lastName
        User user = vehiclePackage.getVehicleProvider().getUser();
        dto.setProviderName(user.getFirstName() + " " + user.getLastName());

        return dto;
    }
}