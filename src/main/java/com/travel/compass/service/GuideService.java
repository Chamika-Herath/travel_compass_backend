package com.travel.compass.service;

import com.travel.compass.Dto.GuideDTO;
import com.travel.compass.model.Guide;
import com.travel.compass.model.GuidePackage;
import com.travel.compass.repository.GuidePackageRepository;
import com.travel.compass.repository.GuideRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideService {

    private final GuideRepository guideRepo;
    private final GuidePackageRepository packageRepo;
    private final ModelMapper modelMapper;

    public GuideService(GuideRepository guideRepo, GuidePackageRepository packageRepo, ModelMapper modelMapper) {
        this.guideRepo = guideRepo;
        this.packageRepo = packageRepo;
        this.modelMapper = modelMapper;
    }

    // ✅ Return a list of simplified GuideDTOs for admin
    public List<GuideDTO> getAllGuides() {
        return guideRepo.findAll().stream().map(guide -> {
            GuideDTO dto = new GuideDTO();
            dto.setId(guide.getId());
            dto.setUserId(guide.getUser().getId());
            dto.setLicenseNumber(guide.getLicenseNumber());
            dto.setAreasOfExpertise(guide.getAreasOfExpertise());
            dto.setLanguagesSpoken(guide.getLanguagesSpoken());
            dto.setDescription(guide.getDescription());
            dto.setVerified(guide.isVerified());
            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ Delete a guide and all their packages
   // public void deleteGuide(Long guideId) {
      //  List<GuidePackage> packages = packageRepo.findByGuideId(guideId);
       // packageRepo.deleteAll(packages);

      //  guideRepo.deleteById(guideId);
   // }
}
