package com.travel.compass.service;

import com.travel.compass.Dto.GuidePackageDTO;
import com.travel.compass.Dto.HotelPackageDTO;
import com.travel.compass.model.*;
import com.travel.compass.repository.HotelOwnerRepository;
import com.travel.compass.repository.HotelPackageRepository;
import com.travel.compass.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelPackageService {

    private final HotelPackageRepository packageRepo;
    private final HotelOwnerRepository hotelOwnerRepo;
    private final LocationRepository locationRepo;
    private final ModelMapper modelMapper;
    private final AuthorizationService authService;

    public HotelPackageService(HotelPackageRepository packageRepo,
                               HotelOwnerRepository hotelOwnerRepo,
                               LocationRepository locationRepo,
                               ModelMapper modelMapper,
                               AuthorizationService authService) {
        this.packageRepo = packageRepo;
        this.hotelOwnerRepo = hotelOwnerRepo;
        this.locationRepo = locationRepo;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    public HotelPackageDTO createPackageByUserId(Long userId, HotelPackageDTO dto, MultipartFile[] images) throws IOException {
        authService.validateUserRole(userId, "ROLE_HOTEL_OWNER");

        HotelOwner hotelOwner = hotelOwnerRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Hotel owner not found for user ID: " + userId));

        HotelPackage hotelPackage = modelMapper.map(dto, HotelPackage.class);
        hotelPackage.setHotelOwner(hotelOwner);

        List<Location> locations = locationRepo.findAllById(dto.getLocationIds());
        if(locations.size() != dto.getLocationIds().size()) {
            throw new RuntimeException("One or more locations not found");
        }
        hotelPackage.setLocations(locations);

        List<String> imagePaths = processImages(images);
        hotelPackage.setImagePaths(imagePaths);

        return modelMapper.map(packageRepo.save(hotelPackage), HotelPackageDTO.class);
    }

    public List<HotelPackageDTO> getPackagesByUserId(Long userId) {
        HotelOwner owner = hotelOwnerRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Hotel owner not found"));
        return packageRepo.findByHotelOwnerId(owner.getId()).stream()
                .map(pkg -> modelMapper.map(pkg, HotelPackageDTO.class))
                .collect(Collectors.toList());
    }

    public List<HotelPackageDTO> getPackagesByLocation(Long locationId) {
        return packageRepo.findByLocationId(locationId).stream()
                .map(pkg -> modelMapper.map(pkg, HotelPackageDTO.class))
                .collect(Collectors.toList());
    }

    public HotelPackageDTO updatePackage(Long packageId, HotelPackageDTO dto) {
        HotelPackage existing = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        modelMapper.map(dto, existing);
        existing.setLocations(locationRepo.findAllById(dto.getLocationIds()));

        return modelMapper.map(packageRepo.save(existing), HotelPackageDTO.class);
    }

    public void deletePackage(Long packageId) {
        HotelPackage pkg = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        if(pkg.getImagePaths() != null) {
            pkg.getImagePaths().forEach(path -> {
                try {
                    Files.deleteIfExists(Paths.get(path.startsWith("/") ? path.substring(1) : path));
                } catch (IOException e) {
                    System.err.println("Failed to delete image: " + path);
                }
            });
        }
        packageRepo.delete(pkg);
    }

    private List<String> processImages(MultipartFile[] images) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        if(images == null || images.length == 0) return imagePaths;

        if(images.length > 5) throw new IllegalArgumentException("Maximum 5 images allowed");

        String uploadDir = "uploads/hotel-packages/";
        Files.createDirectories(Paths.get(uploadDir));

        for(MultipartFile file : images) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            imagePaths.add("/" + uploadDir + filename);
        }
        return imagePaths;
    }


    public HotelPackageDTO getPackageById(Long packageId) {
        HotelPackage hotelPackage = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        HotelPackageDTO dto = modelMapper.map(hotelPackage, HotelPackageDTO.class);

        // Set location IDs
        dto.setLocationIds(hotelPackage.getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));

        // Set guide name using User's firstName + lastName
        User user = hotelPackage.getHotelOwner().getUser();
        dto.setHotelName(user.getFirstName() + " " + user.getLastName());

        return dto;
    }
}