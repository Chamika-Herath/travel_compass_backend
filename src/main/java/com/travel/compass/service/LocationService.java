//package com.travel.compass.service;
//
//import com.travel.compass.model.Location;
//import com.travel.compass.repository.LocationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class LocationService {
//
//    @Autowired
//    private LocationRepository locationRepository;
//
//    public Location saveLocation(Location location) {
//        return locationRepository.save(location);
//    }
//
//    public List<Location> getAllLocations() {
//        return locationRepository.findAll();
//    }
//
//    public Location getLocationById(Long id) {
//        return locationRepository.findById(id).orElse(null);
//    }
//
//    public void deleteLocation(Long id) {
//        locationRepository.deleteById(id);
//    }
//
//    public Location updateLocation(Long id, Location updatedLocation) {
//        Location existing = locationRepository.findById(id).orElseThrow();
//
//        existing.setProvince(updatedLocation.getProvince());
//        existing.setDistrict(updatedLocation.getDistrict());
//        existing.setName(updatedLocation.getName());
//        existing.setCategory(updatedLocation.getCategory());
//        existing.setDescription(updatedLocation.getDescription());
//        existing.setImagePaths(updatedLocation.getImagePaths());
//
//        return locationRepository.save(existing);
//    }
//}
//


package com.travel.compass.service;

import com.travel.compass.model.Location;
import com.travel.compass.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if(updatedLocation.getImagePaths() != null && !updatedLocation.getImagePaths().isEmpty()) {
            existing.setImagePaths(updatedLocation.getImagePaths());
        }

        return locationRepository.save(existing);
    }
}