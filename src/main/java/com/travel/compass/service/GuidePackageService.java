package com.travel.compass.service;

import com.travel.compass.Dto.GuidePackageDTO;
import com.travel.compass.model.*;
import com.travel.compass.repository.GuidePackageRepository;
import com.travel.compass.repository.GuideRepository;
import com.travel.compass.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuidePackageService {

    private final GuidePackageRepository packageRepo;
    private final GuideRepository guideRepo;
    private final LocationRepository locationRepo;
    private final ModelMapper modelMapper;
    private final AuthorizationService authService;

    public GuidePackageService(GuidePackageRepository packageRepo,
                               GuideRepository guideRepo,
                               LocationRepository locationRepo,
                               ModelMapper modelMapper,
                               AuthorizationService authService) {
        this.packageRepo = packageRepo;
        this.guideRepo = guideRepo;
        this.locationRepo = locationRepo;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    public GuidePackageDTO createPackageByUserId(Long userId, GuidePackageDTO dto, MultipartFile[] images) throws IOException {
        authService.validateUserRole(userId, "ROLE_GUIDE");

        Guide guide = guideRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Guide not found for user ID: " + userId));

        GuidePackage guidePackage = new GuidePackage();
        guidePackage.setPackageName(dto.getPackageName());
        guidePackage.setPricePerDay(dto.getPricePerDay());
        guidePackage.setAvailable(dto.isAvailable());
        guidePackage.setGuide(guide);

        List<Location> locations = dto.getLocationIds().stream()
                .map(id -> locationRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id)))
                .collect(Collectors.toList());
        guidePackage.setLocations(locations);

        List<String> imagePaths = new ArrayList<>();
        if (images != null && images.length > 0) {
            String uploadDir = "uploads/guide-packages/";
            Files.createDirectories(Paths.get(uploadDir));

            for (MultipartFile file : images) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + filename);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                imagePaths.add("/" + uploadDir + filename);
            }
        }
        guidePackage.setImagePaths(imagePaths);

        GuidePackage savedPackage = packageRepo.save(guidePackage);
        return modelMapper.map(savedPackage, GuidePackageDTO.class);
    }

    public List<GuidePackageDTO> getPackagesByUserId(Long userId) {
        Guide guide = guideRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));
        return packageRepo.findByGuideId(guide.getId()).stream()
                .map(pkg -> modelMapper.map(pkg, GuidePackageDTO.class))
                .collect(Collectors.toList());
    }

    public GuidePackageDTO updatePackage(Long packageId, GuidePackageDTO dto) {
        GuidePackage existing = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        modelMapper.map(dto, existing);
        existing.setLocations(dto.getLocationIds().stream()
                .map(id -> locationRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Location not found")))
                .collect(Collectors.toList()));

        return modelMapper.map(packageRepo.save(existing), GuidePackageDTO.class);
    }

    public void deletePackage(Long packageId) {
        GuidePackage pkg = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        if (pkg.getImagePaths() != null) {
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




    public GuidePackageDTO getPackageById(Long packageId) {
        GuidePackage guidePackage = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        GuidePackageDTO dto = modelMapper.map(guidePackage, GuidePackageDTO.class);

        // Set location IDs
        dto.setLocationIds(guidePackage.getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));

        // Set guide name using User's firstName + lastName
        User user = guidePackage.getGuide().getUser();
        dto.setGuideName(user.getFirstName() + " " + user.getLastName());

        return dto;
    }
}
