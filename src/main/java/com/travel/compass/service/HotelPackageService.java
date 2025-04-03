package com.travel.compass.service;

import com.travel.compass.model.HotelPackage;
import com.travel.compass.repository.HotelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelPackageService {

    private final HotelPackageRepository repository;

    @Autowired
    public HotelPackageService(HotelPackageRepository repository) {
        this.repository = repository;
    }

    public List<HotelPackage> getAllPackages() {
        return repository.findAll();
    }

    public Optional<HotelPackage> getPackageById(Long id) {
        return repository.findById(id);
    }

    public HotelPackage createPackage(HotelPackage hotelPackage) {
        return repository.save(hotelPackage);
    }

    public HotelPackage updatePackage(Long id, HotelPackage updatedPackage) {
        return repository.findById(id)
                .map(pkg -> {
                    pkg.setName(updatedPackage.getName());
                    pkg.setDescription(updatedPackage.getDescription());
                    pkg.setBedCount(updatedPackage.getBedCount());
                    pkg.setPrice(updatedPackage.getPrice());
                    pkg.setAvailable(updatedPackage.isAvailable());
                    return repository.save(pkg);
                }).orElseThrow(() -> new RuntimeException("Package not found"));
    }

    public void deletePackage(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Package not found");
        }
        repository.deleteById(id);
    }
}
