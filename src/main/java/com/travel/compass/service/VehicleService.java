////package com.travel.compass.service;
////
////import com.travel.compass.model.Vehicle;
////import com.travel.compass.model.VehicleImage;
////import com.travel.compass.repository.VehicleRepository;
////import com.travel.compass.repository.VehicleImageRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////import java.util.List;
////
////@Service
////public class VehicleService {
////
////    @Autowired
////    private VehicleRepository vehicleRepository;
////
////    @Autowired
////    private VehicleImageRepository vehicleImageRepository; // NEW
////
////    public Vehicle saveVehicle(Vehicle vehicle) {
////
////        return vehicleRepository.save(vehicle);
////    }
////
//////    public Vehicle saveVehicle(Vehicle vehicle) {
//////        // Save the vehicle first
//////        Vehicle savedVehicle = vehicleRepository.save(vehicle);
//////
//////        // Save images if any
//////        if (vehicle.getImageUrls() != null && !vehicle.getImageUrls().isEmpty()) {
//////            // Map vehicle image URLs to VehicleImage entities
//////            for (String imageUrl : vehicle.getImageUrls()) {
//////                VehicleImage vehicleImage = new VehicleImage();
//////                vehicleImage.setVehicle(savedVehicle);
//////                vehicleImage.setImageUrl(imageUrl);
//////                vehicleImageRepository.save(vehicleImage);
//////            }
//////        }
//////
//////        return savedVehicle;
//////    }
////
//////    public void saveVehicleImages(List<VehicleImage> vehicleImages) {
//////        vehicleImageRepository.saveAll(vehicleImages); // Save images
//////    }
////
////    public List<Vehicle> getAllVehicles() {
////        return vehicleRepository.findAll();
////    }
////
////    public Vehicle getVehicleById(Long id) {
////        return vehicleRepository.findById(id).orElse(null);
////    }
////
////    public List<VehicleImage> getVehicleImagesByVehicleId(Long vehicleId) {
////        return vehicleImageRepository.findByVehicle_Id(vehicleId); // NEW method to fetch images
////    }
////
////    public void deleteVehicle(Long id) {
////        vehicleRepository.deleteById(id);
////    }
////
////
////}
//
//
//
//package com.travel.compass.service;
//
//import com.travel.compass.model.Vehicle;
//import com.travel.compass.model.VehicleImage;
//import com.travel.compass.repository.VehicleRepository;
//import com.travel.compass.repository.VehicleImageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class VehicleService {
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    @Autowired
//    private VehicleImageRepository vehicleImageRepository;
//
//    /**
//     * Saves a vehicle along with its images.
//     * Ensures transactional consistency.
//     */
//    @Transactional
//    public Vehicle saveVehicle(Vehicle vehicle, List<String> imageUrls) {
//        // Save vehicle first
//        Vehicle savedVehicle = vehicleRepository.save(vehicle);
//
//        // Save images if provided
//        if (imageUrls != null && !imageUrls.isEmpty()) {
////            List<VehicleImage> vehicleImages = new ArrayList<>();
//            for (String imageUrl : imageUrls) {
//                VehicleImage vehicleImage = new VehicleImage();
//                vehicleImage.setVehicle(savedVehicle);
//                vehicleImage.setImageUrl(imageUrl);
//                vehicleImageRepository.save(vehicleImage);
//            }
////            vehicleImageRepository.saveAll(vehicleImages);
//        }
//
//        return savedVehicle;
//    }
//
//    /**
//     * Retrieves all vehicles.
//     */
//    public List<Vehicle> getAllVehicles() {
//        return vehicleRepository.findAll();
//    }
//
//    /**
//     * Retrieves a vehicle by its ID.
//     */
//    public Vehicle getVehicleById(Long id) {
//        return vehicleRepository.findById(id).orElse(null);
//    }
//
//    /**
//     * Retrieves images associated with a vehicle.
//     */
//    public List<VehicleImage> getVehicleImagesByVehicleId(Long vehicleId) {
//        return vehicleImageRepository.findByVehicle_Id(vehicleId);
//    }
//
//    /**
//     * Updates an existing vehicle.
//     */
//    @Transactional
//    public Vehicle updateVehicle(Long vehicleId, Vehicle updatedVehicle, List<String> imageUrls) {
//        Optional<Vehicle> existingVehicleOpt = vehicleRepository.findById(vehicleId);
//
//        if (existingVehicleOpt.isPresent()) {
//            Vehicle existingVehicle = existingVehicleOpt.get();
//
//            // Update vehicle fields
//            existingVehicle.setName(updatedVehicle.getName());
//            existingVehicle.setType(updatedVehicle.getType());
//            existingVehicle.setLicensePlate(updatedVehicle.getLicensePlate());
//            existingVehicle.setDescription(updatedVehicle.getDescription());
//            existingVehicle.setCapacity(updatedVehicle.getCapacity());
//            existingVehicle.setFuelType(updatedVehicle.getFuelType());
//            existingVehicle.setRentalRate(updatedVehicle.getRentalRate());
//            existingVehicle.setLocation(updatedVehicle.getLocation());
//
//            // Save updated vehicle
//            vehicleRepository.save(existingVehicle);
//
//            // Remove old images and save new ones
//            if (imageUrls != null && !imageUrls.isEmpty()) {
//                vehicleImageRepository.deleteByVehicle_Id(vehicleId);
//
//                List<VehicleImage> newImages = new ArrayList<>();
//                for (String imageUrl : imageUrls) {
//                    VehicleImage vehicleImage = new VehicleImage();
//                    vehicleImage.setVehicle(existingVehicle);
//                    vehicleImage.setImageUrl(imageUrl);
//                    newImages.add(vehicleImage);
//                }
//                vehicleImageRepository.saveAll(newImages);
//            }
//
//            return existingVehicle;
//        }
//
//        return null; // Vehicle not found
//    }
//
//    /**
//     * Deletes a vehicle and its associated images.
//     */
//    @Transactional
//    public boolean deleteVehicle(Long id) {
//        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
//
//        if (vehicleOpt.isPresent()) {
//            vehicleImageRepository.deleteByVehicle_Id(id);
//            vehicleRepository.deleteById(id);
//            return true;
//        }
//        return false; // Vehicle not found
//    }
//}
//





package com.travel.compass.service;

import com.travel.compass.model.Vehicle;
import com.travel.compass.model.VehicleImage;
import com.travel.compass.repository.VehicleRepository;
import com.travel.compass.repository.VehicleImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleImageRepository vehicleImageRepository;

    // Save vehicle with images
    public Vehicle saveVehicleWithImages(Vehicle vehicle, List<MultipartFile> files) {
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        saveVehicleImages(savedVehicle, files);
        return savedVehicle;
    }

    // Save vehicle images
    private void saveVehicleImages(Vehicle vehicle, List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) {
            List<VehicleImage> images = new ArrayList<>();
            for (MultipartFile file : files) {
                VehicleImage image = new VehicleImage();
                image.setVehicle(vehicle);
                image.setImageUrl(file.getOriginalFilename()); // Just storing filename, adjust as needed
                images.add(image);
            }
            vehicleImageRepository.saveAll(images);
        }
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get vehicle by ID
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    // Get vehicle images by vehicle ID
    public List<VehicleImage> getVehicleImagesByVehicleId(Long vehicleId) {
        return vehicleImageRepository.findByVehicle_Id(vehicleId);
    }

    // Update vehicle with images
    public void updateVehicleWithImages(Vehicle vehicle, List<MultipartFile> files) {
        // Delete old images
        vehicleImageRepository.deleteByVehicle_Id(vehicle.getId());

        // Save new images
        saveVehicleImages(vehicle, files);

        // Update vehicle
        vehicleRepository.save(vehicle);
    }

    // Delete vehicle
    public void deleteVehicle(Long id) {
        vehicleImageRepository.deleteByVehicle_Id(id); // Delete related images first
        vehicleRepository.deleteById(id); // Then delete vehicle
    }
}



//package com.travel.compass.service;
//
//import com.travel.compass.model.Vehicle;
//import com.travel.compass.repository.VehicleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class VehicleService {
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    public Vehicle saveVehicle(Vehicle vehicle) {
//        return vehicleRepository.save(vehicle);
//    }
//
//    public List<Vehicle> getAllVehicles() {
//        return vehicleRepository.findAll();
//    }
//
//    public Vehicle getVehicleById(Long id) {
//        return vehicleRepository.findById(id).orElse(null);
//    }
//
//    public void deleteVehicle(Long id) {
//        vehicleRepository.deleteById(id);
//    }
//}


