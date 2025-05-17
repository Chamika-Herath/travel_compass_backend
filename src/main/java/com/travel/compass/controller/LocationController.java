//
//
//
//
//
//
//
//
//package com.travel.compass.controller;
//
//import com.travel.compass.Dto.*;
//import com.travel.compass.model.Location;
//import com.travel.compass.service.LocationService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/locations")
//@CrossOrigin(origins = "*")
//public class LocationController {
//
//    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
//    private static final String UPLOAD_DIR = "uploads/location/";
//
//    @Autowired
//    private LocationService locationService;
//
//    @PostMapping("/add")
//    public ResponseEntity<Location> addLocation(
//            @RequestParam("province") String province,
//            @RequestParam("district") String district,
//            @RequestParam("name") String name,
//            @RequestParam("category") String category,
//            @RequestParam("description") String description,
//            @RequestParam("images") List<MultipartFile> images) throws IOException {
//
//        logger.info("Received request to add new location: {}", name);
//        List<String> imagePaths = processUploadedFiles(images);
//
//        Location location = Location.builder()
//                .province(province)
//                .district(district)
//                .name(name)
//                .category(category)
//                .description(description)
//                .imagePaths(imagePaths)
//                .build();
//
//        return ResponseEntity.ok(locationService.saveLocation(location));
//    }
//
////    @GetMapping("/all")
////    public List<Location> getAllLocations() {
////        logger.info("Fetching all locations");
////        return locationService.getAllLocations();
////    }
//
//
//
//
//
//
//
//    /*@GetMapping("/all")
//    public ResponseEntity<List<LocationDisplayDTO>> getAllLocationsWithPackages() {
//        return ResponseEntity.ok(locationService.getAllLocationsWithPackages());
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
//        logger.info("Fetching location with ID: {}", id);
//        Location location = locationService.getLocationById(id);
//        return location != null ? ResponseEntity.ok(location) : ResponseEntity.notFound().build();
//    }*/
//
//
//    // Get all locations with basic info (including images)
//    @GetMapping("/all")
//    public ResponseEntity<List<LocationBasicDTO>> getAllLocations() {
//        return ResponseEntity.ok(locationService.getAllLocationsBasic());
//    }
//
//    // Get guide packages for specific location
//    @GetMapping("/{locationId}/guide-packages")
//    public ResponseEntity<List<GuidePackageDTO>> getGuidePackagesByLocation(@PathVariable Long locationId) {
//        return ResponseEntity.ok(locationService.getGuidePackagesByLocation(locationId));
//    }
//
//    // Get hotel packages for specific location
//    @GetMapping("/{locationId}/hotel-packages")
//    public ResponseEntity<List<HotelPackageDTO>> getHotelPackagesByLocation(@PathVariable Long locationId) {
//        return ResponseEntity.ok(locationService.getHotelPackagesByLocation(locationId));
//    }
//
//    // Get vehicle packages for specific location
//    @GetMapping("/{locationId}/vehicle-packages")
//    public ResponseEntity<List<VehiclePackageDTO>> getVehiclePackagesByLocation(@PathVariable Long locationId) {
//        return ResponseEntity.ok(locationService.getVehiclePackagesByLocation(locationId));
//    }
//}
//
//
//
//
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateLocation(
//            @PathVariable Long id,
//            @RequestParam("province") String province,
//            @RequestParam("district") String district,
//            @RequestParam("name") String name,
//            @RequestParam("category") String category,
//            @RequestParam("description") String description,
//            @RequestParam(value = "images", required = false) List<MultipartFile> images,
//            @RequestParam(value = "imagesToDelete", required = false) List<String> imagesToDelete) {
//
//        logger.info("Updating location with ID: {}", id);
//        try {
//            Location existingLocation = locationService.getLocationById(id);
//            if(existingLocation == null) {
//                logger.warn("Location not found with ID: {}", id);
//                return ResponseEntity.notFound().build();
//            }
//
//            if(imagesToDelete != null && !imagesToDelete.isEmpty()) {
//                deleteImagesFromStorage(imagesToDelete);
//            }
//
//            List<String> newImagePaths = new ArrayList<>();
//            if(images != null && !images.isEmpty()) {
//                newImagePaths = processUploadedFiles(images);
//            }
//
//            List<String> remainingImages = existingLocation.getImagePaths().stream()
//                    .filter(path -> imagesToDelete == null || !imagesToDelete.contains(path))
//                    .collect(Collectors.toList());
//
//            List<String> allImagePaths = new ArrayList<>(remainingImages);
//            allImagePaths.addAll(newImagePaths);
//
//            if(allImagePaths.size() > 5) {
//                logger.error("Image limit exceeded for location ID: {}", id);
//                throw new IllegalArgumentException("Maximum of 5 images allowed");
//            }
//
//            Location updatedLocation = Location.builder()
//                    .province(province)
//                    .district(district)
//                    .name(name)
//                    .category(category)
//                    .description(description)
//                    .imagePaths(allImagePaths)
//                    .build();
//
//            return ResponseEntity.ok(locationService.updateLocation(id, updatedLocation));
//
//        } catch (IOException ex) {
//            logger.error("File operation failed for location ID {}: {}", id, ex.getMessage());
//            return ResponseEntity.internalServerError().body("File processing error");
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
//        logger.info("Deleting location with ID: {}", id);
//        try {
//            Location location = locationService.getLocationById(id);
//            if(location != null && location.getImagePaths() != null) {
//                deleteImagesFromStorage(location.getImagePaths());
//            }
//            locationService.deleteLocation(id);
//            return ResponseEntity.ok().build();
//        } catch (IOException ex) {
//            logger.error("Failed to delete images for location ID {}: {}", id, ex.getMessage());
//            return ResponseEntity.internalServerError().body("Error deleting location files");
//        }
//    }
//
//    private List<String> processUploadedFiles(List<MultipartFile> files) throws IOException {
//        List<String> filePaths = new ArrayList<>();
//        if(files == null || files.isEmpty()) return filePaths;
//
//        if(files.size() > 5) {
//            throw new IllegalArgumentException("Maximum 5 images allowed");
//        }
//
//        for (MultipartFile file : files) {
//            if (!file.isEmpty()) {
//                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
//                Path path = Paths.get(UPLOAD_DIR + filename);
//                Files.createDirectories(path.getParent());
//                Files.write(path, file.getBytes());
//                filePaths.add("/" + UPLOAD_DIR + filename);
//                logger.debug("Stored file: {}", filename);
//            }
//        }
//        return filePaths;
//    }
//
//    private void deleteImagesFromStorage(List<String> imagePaths) throws IOException {
//        for(String imagePath : imagePaths) {
//            try {
//                Path path = Paths.get(imagePath.startsWith("/") ?
//                        imagePath.substring(1) : imagePath);
//                if(Files.exists(path)) {
//                    Files.delete(path);
//                    logger.debug("Deleted file: {}", imagePath);
//                }
//            } catch (IOException ex) {
//                logger.warn("Failed to delete image {}: {}", imagePath, ex.getMessage());
//                throw ex;
//            }
//        }
//    }
//}









package com.travel.compass.controller;

import com.travel.compass.Dto.*;
import com.travel.compass.model.Location;
import com.travel.compass.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
    private static final String UPLOAD_DIR = "uploads/locations/";

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }





    @PostMapping("/add")
    public ResponseEntity<Location> addLocation(
            @RequestParam("province") String province,
            @RequestParam("district") String district,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("images") List<MultipartFile> images) throws IOException {

        logger.info("Received request to add new location: {}", name);
        List<String> imagePaths = processUploadedFiles(images);

        Location location = Location.builder()
                .province(province)
                .district(district)
                .name(name)
                .category(category)
                .description(description)
                .imagePaths(imagePaths)
                .build();

        return ResponseEntity.ok(locationService.saveLocation(location));
    }













    // Get basic location info with images
    @GetMapping("/all")
    public ResponseEntity<List<LocationBasicDTO>> getAllLocationsBasic() {
        return ResponseEntity.ok(locationService.getAllLocationsBasic());
    }

    // Get detailed location info with all packages
    @GetMapping("/{id}")
    public ResponseEntity<LocationDisplayDTO> getLocationDetails(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationDetails(id));
    }

    // Get guide packages for location
    @GetMapping("/{locationId}/guide-packages")
    public ResponseEntity<List<GuidePackageDTO>> getLocationGuidePackages(@PathVariable Long locationId) {
        return ResponseEntity.ok(locationService.getGuidePackagesByLocation(locationId));
    }

    // Get hotel packages for location
    @GetMapping("/{locationId}/hotel-packages")
    public ResponseEntity<List<HotelPackageDTO>> getLocationHotelPackages(@PathVariable Long locationId) {
        return ResponseEntity.ok(locationService.getHotelPackagesByLocation(locationId));
    }

    // Get vehicle packages for location
    @GetMapping("/{locationId}/vehicle-packages")
    public ResponseEntity<List<VehiclePackageDTO>> getLocationVehiclePackages(@PathVariable Long locationId) {
        return ResponseEntity.ok(locationService.getVehiclePackagesByLocation(locationId));
    }

    // Create new location with images
    @PostMapping("/create")
    public ResponseEntity<Location> createLocation(
            @RequestParam("province") String province,
            @RequestParam("district") String district,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("images") List<MultipartFile> images) throws IOException {

        List<String> imagePaths = processUploadedFiles(images);
        Location newLocation = Location.builder()
                .province(province)
                .district(district)
                .name(name)
                .category(category)
                .description(description)
                .imagePaths(imagePaths)
                .build();

        return ResponseEntity.ok(locationService.saveLocation(newLocation));
    }

    // Update location with images
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(
            @PathVariable Long id,
            @RequestParam("province") String province,
            @RequestParam("district") String district,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam(value = "images", required = false) List<MultipartFile> newImages,
            @RequestParam(value = "imagesToDelete", required = false) List<String> imagesToDelete) {

        try {
            Location existing = locationService.getLocationById(id);
            if(existing == null) {
                return ResponseEntity.notFound().build();
            }

            // Handle image deletions
            if(imagesToDelete != null && !imagesToDelete.isEmpty()) {
                deleteImagesFromStorage(imagesToDelete);
                existing.getImagePaths().removeAll(imagesToDelete);
            }

            // Process new images
            List<String> newImagePaths = new ArrayList<>();
            if(newImages != null && !newImages.isEmpty()) {
                newImagePaths = processUploadedFiles(newImages);
            }

            // Update location data
            existing.setProvince(province);
            existing.setDistrict(district);
            existing.setName(name);
            existing.setCategory(category);
            existing.setDescription(description);
            existing.getImagePaths().addAll(newImagePaths);

            return ResponseEntity.ok(locationService.updateLocation(id, existing));

        } catch (IOException ex) {
            logger.error("File operation failed: {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("Error processing files");
        }
    }

    // Delete location
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        try {
            Location location = locationService.getLocationById(id);
            if(location != null && location.getImagePaths() != null) {
                deleteImagesFromStorage(location.getImagePaths());
            }
            locationService.deleteLocation(id);
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            logger.error("Delete failed: {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("Error deleting location");
        }
    }

    // Image handling utilities
    private List<String> processUploadedFiles(List<MultipartFile> files) throws IOException {
        List<String> filePaths = new ArrayList<>();
        if(files == null || files.isEmpty()) return filePaths;

        if(files.size() > 5) throw new IllegalArgumentException("Maximum 5 images allowed");

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + filename);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                filePaths.add("/" + UPLOAD_DIR + filename);
            }
        }
        return filePaths;
    }

    private void deleteImagesFromStorage(List<String> imagePaths) throws IOException {
        for(String imagePath : imagePaths) {
            try {
                Path path = Paths.get(imagePath.startsWith("/") ?
                        imagePath.substring(1) : imagePath);
                if(Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (IOException ex) {
                logger.warn("Failed to delete image: {}", imagePath);
                throw ex;
            }
        }
    }
}