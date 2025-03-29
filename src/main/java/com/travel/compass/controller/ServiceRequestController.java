//package com.travel.compass.controller;
//import com.travel.compass.Dto.ServiceRequestDTO;
//import com.travel.compass.model.ServiceRequest;
//import com.travel.compass.model.User;
//import com.travel.compass.repository.ServiceRequestRepository;
//import com.travel.compass.repository.UserRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/service-requests")
//public class ServiceRequestController {
//
//    private final ServiceRequestRepository serviceRequestRepository;
//    private final UserRepository userRepository;  // Inject UserRepository
//
//    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository) {
//        this.serviceRequestRepository = serviceRequestRepository;
//        this.userRepository = userRepository;
//    }
//
//    // Submit a service request
//    @PostMapping("/submit")
//    public ResponseEntity<?> submitRequest(@RequestBody ServiceRequestDTO requestDTO) {
//        Optional<User> user = userRepository.findById(requestDTO.getUserId());
//        if (user.isEmpty()) {
//            return ResponseEntity.badRequest().body("User not found");
//        }
//
//        ServiceRequest request = new ServiceRequest();
//        request.setUser(user.get());
//        request.setFullName(requestDTO.getFullName());
//        request.setAddress(requestDTO.getAddress());
//        request.setNic(requestDTO.getNic());
//        request.setServiceType(requestDTO.getServiceType());
//        request.setDescription(requestDTO.getDescription());
//        request.setPhoneNumber(requestDTO.getPhoneNumber());
//        request.setStatus("PENDING");
//
//        serviceRequestRepository.save(request);
//        return ResponseEntity.ok("Service request submitted successfully!");
//    }
//
//
//
//    // Get all pending requests
//    @GetMapping("/pending")
//    public ResponseEntity<List<ServiceRequest>> getPendingRequests() {
//        List<ServiceRequest> pendingRequests = serviceRequestRepository.findByStatus("PENDING");
//        return ResponseEntity.ok(pendingRequests);
//    }
//
//
//}


package com.travel.compass.controller;

import com.travel.compass.Dto.ServiceRequestDTO;
import com.travel.compass.model.ServiceRequest;
import com.travel.compass.model.User;
import com.travel.compass.repository.ServiceRequestRepository;
import com.travel.compass.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestController.class);

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
    }

    // ✅ Submit a service request
    @PostMapping("/submit")
    public ResponseEntity<?> submitRequest(@RequestBody ServiceRequestDTO requestDTO) {
        Optional<User> user = userRepository.findById(requestDTO.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        ServiceRequest request = new ServiceRequest();
        request.setUser(user.get());
        request.setFullName(requestDTO.getFullName());
        request.setAddress(requestDTO.getAddress());
        request.setNic(requestDTO.getNic());
        request.setServiceType(requestDTO.getServiceType());
        request.setDescription(requestDTO.getDescription());
        request.setPhoneNumber(requestDTO.getPhoneNumber());
        request.setStatus("PENDING");

        serviceRequestRepository.save(request);
        logger.info("Service request submitted for user ID: {}", requestDTO.getUserId());
        return ResponseEntity.ok("Service request submitted successfully!");
    }

    // ✅ Get all pending requests
    @GetMapping("/pending")
    public ResponseEntity<List<ServiceRequest>> getPendingRequests() {
        List<ServiceRequest> pendingRequests = serviceRequestRepository.findByStatus("PENDING");
        logger.info("Fetching all pending service requests...");
        return ResponseEntity.ok(pendingRequests);
    }

    // ✅ Approve or reject a request & update user role
    @PutMapping("/{requestId}/status/{status}")
    public ResponseEntity<?> updateRequestStatus(@PathVariable Long requestId, @PathVariable String status) {
        logger.info("Updating status of request ID: {} to {}", requestId, status);
        Optional<ServiceRequest> optionalRequest = serviceRequestRepository.findById(requestId);

        if (optionalRequest.isPresent()) {
            ServiceRequest request = optionalRequest.get();
            request.setStatus(status.toUpperCase());

            if ("APPROVED".equalsIgnoreCase(status)) {
                User user = request.getUser();
                String newRole = mapServiceTypeToRole(request.getServiceType());
                user.setRole(newRole);
                userRepository.save(user);
                logger.info("User ID {} role updated to {}", user.getId(), newRole);
            }

            serviceRequestRepository.save(request);
            return ResponseEntity.ok("Request ID " + requestId + " status updated to " + status);
        } else {
            return ResponseEntity.badRequest().body("Service request not found for ID: " + requestId);
        }
    }

    // ✅ Map service type to user roles
    private String mapServiceTypeToRole(String serviceType) {
        return switch (serviceType.toUpperCase()) {
            case "VEHICLE_PROVIDER" -> "ROLE_DRIVER";
            case "HOTEL_OWNER" -> "ROLE_HOTEL_OWNER";
            case "GUIDE" -> "ROLE_GUIDE";
            default -> "ROLE_USER";
        };
    }
}
