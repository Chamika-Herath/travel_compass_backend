package com.travel.compass.service;

import com.travel.compass.Dto.*;
import com.travel.compass.model.*;
import com.travel.compass.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final GuidePackageRepository guidePackageRepository;
    private final HotelPackageRepository hotelPackageRepository;
    private final VehiclePackageRepository vehiclePackageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository,
                           GuidePackageRepository guidePackageRepository,
                           HotelPackageRepository hotelPackageRepository,
                           VehiclePackageRepository vehiclePackageRepository,
                           ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.guidePackageRepository = guidePackageRepository;
        this.hotelPackageRepository = hotelPackageRepository;
        this.vehiclePackageRepository = vehiclePackageRepository;
        this.modelMapper = modelMapper;
    }

    // Get basic location info with images (without packages)
    public List<LocationBasicDTO> getAllLocationsBasic() {
        return locationRepository.findAll().stream()
                .map(location -> modelMapper.map(location, LocationBasicDTO.class))
                .collect(Collectors.toList());
    }

    // Get detailed location info with all packages
    public LocationDisplayDTO getLocationDetails(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        return convertToDisplayDTO(location);
    }

    // Get guide packages for a specific location
    public List<GuidePackageDTO> getGuidePackagesByLocation(Long locationId) {
        return guidePackageRepository.findByLocationId(locationId).stream()
                .map(this::convertToGuidePackageDTO)
                .collect(Collectors.toList());
    }

    // Get hotel packages for a specific location
    public List<HotelPackageDTO> getHotelPackagesByLocation(Long locationId) {
        return hotelPackageRepository.findByLocationId(locationId).stream()
                .map(this::convertToHotelPackageDTO)
                .collect(Collectors.toList());
    }

    // Get vehicle packages for a specific location
    public List<VehiclePackageDTO> getVehiclePackagesByLocation(Long locationId) {
        return vehiclePackageRepository.findByLocationId(locationId).stream()
                .map(this::convertToVehiclePackageDTO)
                .collect(Collectors.toList());
    }

    // Conversion methods
    private LocationDisplayDTO convertToDisplayDTO(Location location) {
        LocationDisplayDTO dto = modelMapper.map(location, LocationDisplayDTO.class);
        dto.setGuidePackages(mapGuidePackages(location.getGuidePackages()));
        dto.setHotelPackages(mapHotelPackages(location.getHotelPackages()));
        dto.setVehiclePackages(mapVehiclePackages(location.getVehiclePackages()));
        return dto;
    }

    private List<GuidePackageDTO> mapGuidePackages(List<GuidePackage> packages) {
        return packages.stream()
                .map(this::convertToGuidePackageDTO)
                .collect(Collectors.toList());
    }

    private List<HotelPackageDTO> mapHotelPackages(List<HotelPackage> packages) {
        return packages.stream()
                .map(this::convertToHotelPackageDTO)
                .collect(Collectors.toList());
    }

    private List<VehiclePackageDTO> mapVehiclePackages(List<VehiclePackage> packages) {
        return packages.stream()
                .map(this::convertToVehiclePackageDTO)
                .collect(Collectors.toList());
    }

    private GuidePackageDTO convertToGuidePackageDTO(GuidePackage pkg) {
        GuidePackageDTO dto = modelMapper.map(pkg, GuidePackageDTO.class);
        dto.setLocationIds(pkg.getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    private HotelPackageDTO convertToHotelPackageDTO(HotelPackage pkg) {
        HotelPackageDTO dto = modelMapper.map(pkg, HotelPackageDTO.class);
        dto.setLocationIds(pkg.getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    private VehiclePackageDTO convertToVehiclePackageDTO(VehiclePackage pkg) {
        VehiclePackageDTO dto = modelMapper.map(pkg, VehiclePackageDTO.class);
        dto.setLocationIds(pkg.getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));
        dto.setVehicleType(pkg.getVehicleType().name());
        dto.setFuelType(pkg.getFuelType().name());
        return dto;
    }

    // Existing CRUD operations
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
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        modelMapper.map(updatedLocation, existing);
        if(updatedLocation.getImagePaths() != null) {
            existing.setImagePaths(updatedLocation.getImagePaths());
        }
        return locationRepository.save(existing);
    }
}