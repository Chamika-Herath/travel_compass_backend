//package com.travel.compass.controller;
//
//import com.travel.compass.model.Vehicle;
//import com.travel.compass.model.VehicleImage;
//import com.travel.compass.service.VehicleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/vehicles")
////@CrossOrigin(origins = "http://localhost:3000")
//public class VehicleController {
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    // Handle the POST request to save vehicle data along with images
//    @PostMapping("/register")
//    public ResponseEntity<String> registerVehicle(
//            @RequestParam("name") String name,
//            @RequestParam("type") String type,
//            @RequestParam("licensePlate") String licensePlate,
//            @RequestParam("description") String description,
//            @RequestParam("capacity") int capacity,
//            @RequestParam("fuelType") String fuelType,
//            @RequestParam("rentalRate") double rentalRate,
//            @RequestParam("location") String location,
////            @RequestParam("files") List<MultipartFile> files)
//            @RequestParam(value = "files", required = false) List<MultipartFile> files){
//
//        try {
//            Vehicle vehicle = new Vehicle();
//            vehicle.setName(name);
//            vehicle.setType(type);
//            vehicle.setLicensePlate(licensePlate);
//            vehicle.setDescription(description);
//            vehicle.setCapacity(capacity);
//            vehicle.setFuelType(fuelType);
//            vehicle.setRentalRate(rentalRate);
//            vehicle.setLocation(location);
//
//            // Save vehicle first to generate ID
//            Vehicle savedVehicle = vehicleService.saveVehicle(vehicle, imageUrls);
//
//            // Save images if provided
//            if (files != null && !files.isEmpty()) {
//                List<VehicleImage> vehicleImages = new ArrayList<>();
//                for (MultipartFile file : files) {
//                    String imageUrl = vehicleService.saveImage(file); // Save image file and get URL
//                    VehicleImage vehicleImage = new VehicleImage();
//                    vehicleImage.setVehicle(savedVehicle);
//                    vehicleImage.setImageUrl(imageUrl);
//                    vehicleImages.add(vehicleImage);
//                }
//                vehicleService.saveVehicleImages(vehicleImages);
//            }
//
//            return ResponseEntity.ok("Vehicle Registered Successfully!");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error while uploading vehicle data: " + e.getMessage());
////            // Logic for saving images, for now, we will just store filenames
////            List<String> imageUrls = new ArrayList<>();
////            for (MultipartFile file : files) {
////                // In a real scenario, you would save the file to a server or cloud and get the URL
////                // For now, we just store the file name
////                imageUrls.add(file.getOriginalFilename());
////            }
////
////            Vehicle vehicle = new Vehicle();
////            vehicle.setName(name);
////            vehicle.setType(type);
////            vehicle.setLicensePlate(licensePlate);
////            vehicle.setDescription(description);
////            vehicle.setCapacity(capacity);
////            vehicle.setFuelType(fuelType);
////            vehicle.setRentalRate(rentalRate);
////            vehicle.setLocation(location);
////            vehicle.setImageUrls(imageUrls);
////
////            vehicleService.saveVehicle(vehicle);
////
////            return ResponseEntity.ok("Vehicle Registered Successfully!");
////
////        } catch (Exception e) {
////            return ResponseEntity.status(500).body("Error while uploading vehicle data: " + e.getMessage());
//        }
//
//    }
//
//    // Get all vehicles (Read)
//    @GetMapping
//    public ResponseEntity<List<Vehicle>> getAllVehicles() {
//        try {
//            List<Vehicle> vehicles = vehicleService.getAllVehicles();
//            return ResponseEntity.ok(vehicles);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//
//    // Get a specific vehicle by ID (Read)
//    @GetMapping("/{vehicleId}")
//    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId) {
//        try {
//            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
//            if (vehicle == null) {
//                return ResponseEntity.status(404).body(null); // Vehicle not found
//            }
//            return ResponseEntity.ok(vehicle); // Return vehicle details
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//
//    // Get vehicle images by vehicleId
//    @GetMapping("/{vehicleId}/images")
//    public ResponseEntity<List<VehicleImage>> getVehicleImages(@PathVariable Long vehicleId) {
//        List<VehicleImage> images = vehicleService.getVehicleImagesByVehicleId(vehicleId);
//        if (images.isEmpty()) {
//            return ResponseEntity.ok(Collections.emptyList()); // Return empty JSON array
//        }
//        return ResponseEntity.ok(images);
//    }
//
//
//    // Update vehicle details (Update)
//    @PutMapping("/{vehicleId}")
//    public ResponseEntity<String> updateVehicle(@PathVariable Long vehicleId,
//                                                @RequestParam("name") String name,
//                                                @RequestParam("type") String type,
//                                                @RequestParam("licensePlate") String licensePlate,
//                                                @RequestParam("description") String description,
//                                                @RequestParam("capacity") int capacity,
//                                                @RequestParam("fuelType") String fuelType,
//                                                @RequestParam("rentalRate") double rentalRate,
//                                                @RequestParam("location") String location,
////                                                @RequestParam("files") List<MultipartFile> files),
//                                                @RequestParam(value = "files", required = false) List<MultipartFile> files) {
//
//        try {
//            Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);
//            if (existingVehicle == null) {
//                return ResponseEntity.status(404).body("Vehicle not found");
//            }
//
//            // Update fields of the existing vehicle
//            existingVehicle.setName(name);
//            existingVehicle.setType(type);
//            existingVehicle.setLicensePlate(licensePlate);
//            existingVehicle.setDescription(description);
//            existingVehicle.setCapacity(capacity);
//            existingVehicle.setFuelType(fuelType);
//            existingVehicle.setRentalRate(rentalRate);
//            existingVehicle.setLocation(location);
//
//            // Remove old images if new ones are provided
//            if (files != null && !files.isEmpty()) {
//                vehicleService.deleteVehicleImages(vehicleId); // Delete old images
//
//                List<VehicleImage> vehicleImages = new ArrayList<>();
//                for (MultipartFile file : files) {
//                    String imageUrl = vehicleService.saveImage(file);
//                    VehicleImage vehicleImage = new VehicleImage();
//                    vehicleImage.setVehicle(existingVehicle);
//                    vehicleImage.setImageUrl(imageUrl);
//                    vehicleImages.add(vehicleImage);
//                }
//                vehicleService.saveVehicleImages(vehicleImages);
//            }
//
//            vehicleService.saveVehicle(existingVehicle);
//
//            return ResponseEntity.ok("Vehicle updated successfully!");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error updating vehicle: " + e.getMessage());
////            Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);
////            if (existingVehicle == null) {
////                return ResponseEntity.status(404).body("Vehicle not found");
////            }
////
////            // Update fields of the existing vehicle
////            existingVehicle.setName(name);
////            existingVehicle.setType(type);
////            existingVehicle.setLicensePlate(licensePlate);
////            existingVehicle.setDescription(description);
////            existingVehicle.setCapacity(capacity);
////            existingVehicle.setFuelType(fuelType);
////            existingVehicle.setRentalRate(rentalRate);
////            existingVehicle.setLocation(location);
////
////            // Logic for updating images
////            List<String> imageUrls = new ArrayList<>();
////            for (MultipartFile file : files) {
////                imageUrls.add(file.getOriginalFilename());  // Save only the filename
////            }
////            existingVehicle.setImageUrls(imageUrls);  // Set updated image URLs
////
////            // Save the updated vehicle
////            vehicleService.saveVehicle(existingVehicle);
////
////            return ResponseEntity.ok("Vehicle updated successfully!");
////        } catch (Exception e) {
////            return ResponseEntity.status(500).body("Error updating vehicle: " + e.getMessage());
//        }
//    }
//
//    // Delete vehicle (Delete)
//    @DeleteMapping("/{vehicleId}")
//    public ResponseEntity<String> deleteVehicle(@PathVariable Long vehicleId) {
//        try {
//            Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);
//            if (existingVehicle == null) {
//                return ResponseEntity.status(404).body("Vehicle not found");
//            }
//
//            // Delete the vehicle
//            vehicleService.deleteVehicle(vehicleId);
//
//            return ResponseEntity.ok("Vehicle deleted successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error deleting vehicle: " + e.getMessage());
//        }
//    }
//}







package com.travel.compass.controller;

import com.travel.compass.model.Vehicle;
import com.travel.compass.model.VehicleImage;
import com.travel.compass.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Register a new vehicle with images
    @PostMapping("/register")
    public ResponseEntity<String> registerVehicle(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("licensePlate") String licensePlate,
            @RequestParam("description") String description,
            @RequestParam("capacity") int capacity,
            @RequestParam("fuelType") String fuelType,
            @RequestParam("rentalRate") double rentalRate,
            @RequestParam("location") String location,
            @RequestParam("files") List<MultipartFile> files) {

        try {
            Vehicle vehicle = new Vehicle();
            vehicle.setName(name);
            vehicle.setType(type);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setDescription(description);
            vehicle.setCapacity(capacity);
            vehicle.setFuelType(fuelType);
            vehicle.setRentalRate(rentalRate);
            vehicle.setLocation(location);

            // Save vehicle and images
            vehicleService.saveVehicleWithImages(vehicle, files);

            return ResponseEntity.ok("Vehicle registered successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while uploading vehicle data: " + e.getMessage());
        }
    }

    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // Get a specific vehicle by ID
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
    }

    // Get vehicle images
    @GetMapping("/{vehicleId}/images")
    public ResponseEntity<List<VehicleImage>> getVehicleImages(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(vehicleService.getVehicleImagesByVehicleId(vehicleId));
    }


    // Update vehicle details & images
    @PutMapping("/{vehicleId}")
    public ResponseEntity<String> updateVehicle(
            @PathVariable Long vehicleId,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("licensePlate") String licensePlate,
            @RequestParam("description") String description,
            @RequestParam("capacity") int capacity,
            @RequestParam("fuelType") String fuelType,
            @RequestParam("rentalRate") double rentalRate,
            @RequestParam("location") String location,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        try {
            Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);
            if (existingVehicle == null) {
                return ResponseEntity.status(404).body("Vehicle not found");
            }

            // Update vehicle details
            existingVehicle.setName(name);
            existingVehicle.setType(type);
            existingVehicle.setLicensePlate(licensePlate);
            existingVehicle.setDescription(description);
            existingVehicle.setCapacity(capacity);
            existingVehicle.setFuelType(fuelType);
            existingVehicle.setRentalRate(rentalRate);
            existingVehicle.setLocation(location);

            // Update vehicle and images
            vehicleService.updateVehicleWithImages(existingVehicle, files);

            return ResponseEntity.ok("Vehicle updated successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating vehicle: " + e.getMessage());
        }
    }

    // Delete vehicle
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long vehicleId) {
        try {
            vehicleService.deleteVehicle(vehicleId);
            return ResponseEntity.ok("Vehicle deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting vehicle: " + e.getMessage());
        }
    }
}




//package com.travel.compass.controller;
//
//import com.travel.compass.model.Vehicle;
//import com.travel.compass.service.VehicleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/vehicles")
//@CrossOrigin(origins = "http://localhost:3000")
//public class VehicleController {
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    // Register a new vehicle (WITHOUT images)
//    @PostMapping("/register")
//    public ResponseEntity<String> registerVehicle(@RequestBody Vehicle vehicle) {
//        try {
//            vehicleService.saveVehicle(vehicle);
//            return ResponseEntity.ok("Vehicle registered successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error while registering vehicle: " + e.getMessage());
//        }
//    }
//
//    // Get all vehicles
//    @GetMapping
//    public ResponseEntity<List<Vehicle>> getAllVehicles() {
//        return ResponseEntity.ok(vehicleService.getAllVehicles());
//    }
//
//    // Get a specific vehicle by ID
//    @GetMapping("/{vehicleId}")
//    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId) {
//        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
//        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
//    }
//
//    // Update vehicle details
//    @PutMapping("/{vehicleId}")
//    public ResponseEntity<String> updateVehicle(@PathVariable Long vehicleId, @RequestBody Vehicle updatedVehicle) {
//        try {
//            Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);
//            if (existingVehicle == null) {
//                return ResponseEntity.status(404).body("Vehicle not found");
//            }
//
//            // Update vehicle details
//            existingVehicle.setName(updatedVehicle.getName());
//            existingVehicle.setType(updatedVehicle.getType());
//            existingVehicle.setLicensePlate(updatedVehicle.getLicensePlate());
//            existingVehicle.setDescription(updatedVehicle.getDescription());
//            existingVehicle.setCapacity(updatedVehicle.getCapacity());
//            existingVehicle.setFuelType(updatedVehicle.getFuelType());
//            existingVehicle.setRentalRate(updatedVehicle.getRentalRate());
//            existingVehicle.setLocation(updatedVehicle.getLocation());
//
//            vehicleService.saveVehicle(existingVehicle);
//            return ResponseEntity.ok("Vehicle updated successfully!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error updating vehicle: " + e.getMessage());
//        }
//    }
//
//    // Delete vehicle
//    @DeleteMapping("/{vehicleId}")
//    public ResponseEntity<String> deleteVehicle(@PathVariable Long vehicleId) {
//        try {
//            vehicleService.deleteVehicle(vehicleId);
//            return ResponseEntity.ok("Vehicle deleted successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error deleting vehicle: " + e.getMessage());
//        }
//    }
//}

