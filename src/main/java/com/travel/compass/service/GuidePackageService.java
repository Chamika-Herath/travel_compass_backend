package com.travel.compass.service;

import com.travel.compass.Dto.GuidePackageDTO;
import com.travel.compass.model.Guide;
import com.travel.compass.model.GuidePackage;
import com.travel.compass.repository.GuidePackageRepository;
import com.travel.compass.repository.GuideRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuidePackageService {

    private final GuidePackageRepository packageRepo;
    private final GuideRepository guideRepo;
    private final ModelMapper modelMapper;

    public GuidePackageService(GuidePackageRepository packageRepo, GuideRepository guideRepo, ModelMapper modelMapper) {
        this.packageRepo = packageRepo;
        this.guideRepo = guideRepo;
        this.modelMapper = modelMapper;
    }

    public GuidePackageDTO createPackage(Long guideId, GuidePackageDTO dto, MultipartFile[] images) throws IOException {
        Guide guide = guideRepo.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        GuidePackage guidePackage = modelMapper.map(dto, GuidePackage.class);
        guidePackage.setGuide(guide);

        List<String> savedImagePaths = new ArrayList<>();
        String uploadDir = "uploads/guide-packages/";
        File directory = new File(uploadDir);
        if (!directory.exists()) directory.mkdirs();

        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + filename);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                savedImagePaths.add("/" + uploadDir + filename);
            }
        }

        guidePackage.setImagePaths(savedImagePaths);
        GuidePackage saved = packageRepo.save(guidePackage);

        return modelMapper.map(saved, GuidePackageDTO.class);
    }

    public List<GuidePackageDTO> getPackagesByGuide(Long guideId) {
        return packageRepo.findByGuideId(guideId).stream()
                .map(pkg -> modelMapper.map(pkg, GuidePackageDTO.class))
                .collect(Collectors.toList());
    }

    public GuidePackageDTO updatePackage(Long packageId, GuidePackageDTO dto) {
        GuidePackage existing = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        existing.setPackageName(dto.getPackageName());
        existing.setLocation(dto.getLocation());
        existing.setPlaces(dto.getPlaces());
        existing.setPricePerDay(dto.getPricePerDay());
        existing.setAvailable(dto.isAvailable());

        return modelMapper.map(packageRepo.save(existing), GuidePackageDTO.class);
    }

    public void deletePackage(Long packageId) {
        packageRepo.deleteById(packageId);
    }
}
