//package com.travel.compass.ServiceImpl;
//
//
//import com.travel.compass.Dto.GuidePackageDTO;
//import com.travel.compass.model.Guide;
//import com.travel.compass.model.GuidePackage;
//import com.travel.compass.repository.GuidePackageRepository;
//import com.travel.compass.repository.GuideRepository;
//import com.travel.compass.service.GuidePackageService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class GuidePackageServiceImpl implements GuidePackageService {
//
//    private final GuidePackageRepository packageRepository;
//    private final GuideRepository guideRepository;
//    private final ModelMapper modelMapper;
//
//    @Override
//    @Transactional
//    public GuidePackageDTO addPackage(GuidePackageDTO dto) {
//        Guide guide = guideRepository.findById(dto.getGuideId())
//                .orElseThrow(() -> new RuntimeException("Guide not found"));
//
//        GuidePackage guidePackage = modelMapper.map(dto, GuidePackage.class);
//        guidePackage.setGuide(guide);
//
//        GuidePackage saved = packageRepository.save(guidePackage);
//        return modelMapper.map(saved, GuidePackageDTO.class);
//    }
//
//    @Override
//    @Transactional
//    public GuidePackageDTO updatePackage(Long id, GuidePackageDTO dto) {
//        GuidePackage existing = packageRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Package not found"));
//
//        existing.setPackageName(dto.getPackageName());
//        existing.setLocation(dto.getLocation());
//        existing.setPlaces(dto.getPlaces());
//        existing.setPricePerDay(dto.getPricePerDay());
//        existing.setAvailable(dto.isAvailable());
//
//        GuidePackage updated = packageRepository.save(existing);
//        return modelMapper.map(updated, GuidePackageDTO.class);
//    }
//
//    @Override
//    public void deletePackage(Long id) {
//        packageRepository.deleteById(id);
//    }
//
//    @Override
//    public List<GuidePackageDTO> getPackagesByGuideId(Long guideId) {
//        return packageRepository.findByGuideId(guideId).stream()
//                .map(pkg -> modelMapper.map(pkg, GuidePackageDTO.class))
//                .collect(Collectors.toList());
//    }
//}
