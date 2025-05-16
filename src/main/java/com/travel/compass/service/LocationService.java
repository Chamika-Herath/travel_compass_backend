

package com.travel.compass.service;

import com.travel.compass.Dto.GuidePackageDTO;
import com.travel.compass.Dto.LocationDisplayDTO;
import com.travel.compass.model.Location;
import com.travel.compass.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public Location updateLocation(Long id, Location updatedLocation) {
        Location existing = locationRepository.findById(id).orElseThrow();

        existing.setProvince(updatedLocation.getProvince());
        existing.setDistrict(updatedLocation.getDistrict());
        existing.setName(updatedLocation.getName());
        existing.setCategory(updatedLocation.getCategory());
        existing.setDescription(updatedLocation.getDescription());

        if (updatedLocation.getImagePaths() != null && !updatedLocation.getImagePaths().isEmpty()) {
            existing.setImagePaths(updatedLocation.getImagePaths());
        }

        return locationRepository.save(existing);
    }

    public List<LocationDisplayDTO> getAllLocationsWithPackages() {
        return locationRepository.findAll().stream().map(location -> {
            List<GuidePackageDTO> guidePackages = location.getGuidePackages().stream().map(pkg -> {
                GuidePackageDTO dto = new GuidePackageDTO();
                dto.setId(pkg.getId());
                dto.setPackageName(pkg.getPackageName());
                dto.setLocationIds(pkg.getLocations().stream().map(loc -> loc.getId()).collect(Collectors.toList()));
                dto.setPricePerDay(pkg.getPricePerDay());
                dto.setAvailable(pkg.isAvailable());
                dto.setImagePaths(pkg.getImagePaths());
                return dto;
            }).collect(Collectors.toList());

            LocationDisplayDTO locDTO = new LocationDisplayDTO();
            locDTO.setId(location.getId());
            locDTO.setProvince(location.getProvince());
            locDTO.setDistrict(location.getDistrict());
            locDTO.setName(location.getName());
            locDTO.setCategory(location.getCategory());
            locDTO.setDescription(location.getDescription());
            locDTO.setImagePaths(location.getImagePaths());
            locDTO.setGuidePackages(guidePackages);

            return locDTO;
        }).collect(Collectors.toList());
    }
}
