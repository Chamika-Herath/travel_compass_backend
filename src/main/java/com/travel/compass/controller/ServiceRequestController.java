package com.travel.compass.controller;
import com.travel.compass.Dto.ServiceRequestDTO;
import com.travel.compass.model.ServiceRequest;
import com.travel.compass.model.User;
import com.travel.compass.repository.ServiceRequestRepository;
import com.travel.compass.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;  // Inject UserRepository

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
    }

    // Submit a service request
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
        return ResponseEntity.ok("Service request submitted successfully!");
    }



    // Get all pending requests
    @GetMapping("/pending")
    public ResponseEntity<List<ServiceRequest>> getPendingRequests() {
        List<ServiceRequest> pendingRequests = serviceRequestRepository.findByStatus("PENDING");
        return ResponseEntity.ok(pendingRequests);
    }


}
