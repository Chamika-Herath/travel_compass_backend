package com.travel.compass.controller;

import com.travel.compass.Dto.ServiceRequestDTO;
import com.travel.compass.model.ServiceRequest;
import com.travel.compass.model.User;
import com.travel.compass.repository.ServiceRequestRepository;
import com.travel.compass.repository.UserRepository;
import com.travel.compass.service.ServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestController.class);

    private final ServiceRequestService serviceRequestService;
    private final UserRepository userRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ModelMapper modelMapper;

    // ========== Create Endpoints ========== //

    @PostMapping
    public ResponseEntity<?> createServiceRequest(@Valid @RequestBody ServiceRequestDTO requestDTO) {
        try {
            User user = userRepository.findById(requestDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ServiceRequest request = new ServiceRequest();
            request.setUser(user);
            request.setFullName(requestDTO.getFullName());
            request.setAddress(requestDTO.getAddress());
            request.setNic(requestDTO.getNic());
            request.setServiceType(requestDTO.getServiceType());
            request.setDescription(requestDTO.getDescription());
            request.setPhoneNumber(requestDTO.getPhoneNumber());
            request.setLicenseNumber(requestDTO.getLicenseNumber());
            request.setBusinessName(requestDTO.getBusinessName());
            request.setVehicleTypes(requestDTO.getVehicleTypes());

            ServiceRequest createdRequest = serviceRequestService.createServiceRequest(request);
            return ResponseEntity.ok(createdRequest);
        } catch (Exception e) {
            logger.error("Error creating service request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to create request",
                    "message", e.getMessage()
            ));
        }
    }

    // ========== Read Endpoints ========== //

    /*@GetMapping("/pending")
    public ResponseEntity<List<ServiceRequest>> getAllPendingRequests() {
        List<ServiceRequest> requests = serviceRequestService.getAllPendingServiceRequests();
        return ResponseEntity.ok(requests);
    }*/
    @GetMapping("/pending")
    public ResponseEntity<List<ServiceRequestDTO>> getPendingRequests() {
        List<ServiceRequest> requests = serviceRequestRepository.findByStatus("PENDING");

        List<ServiceRequestDTO> dtos = requests.stream()
                .map(request -> modelMapper.map(request, ServiceRequestDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequests() {
        List<ServiceRequest> requests = serviceRequestService.getAllServiceRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceRequestById(@PathVariable Long id) {
        Optional<ServiceRequest> request = serviceRequestService.getServiceRequestById(id);
        return request.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ServiceRequest>> getServiceRequestsByUser(@PathVariable Long userId) {
        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    // ========== Update Endpoints ========== //

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveServiceRequest(@PathVariable Long id) {
        try {
            ServiceRequest approvedRequest = serviceRequestService.approveServiceRequest(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Request approved successfully",
                    "requestId", approvedRequest.getId(),
                    "userId", approvedRequest.getUser().getId(),
                    "newRole", approvedRequest.getUser().getRole()
            ));
        } catch (Exception e) {
            logger.error("Error approving request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Approval failed",
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectServiceRequest(@PathVariable Long id) {
        try {
            ServiceRequest rejectedRequest = serviceRequestService.rejectServiceRequest(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Request rejected",
                    "requestId", rejectedRequest.getId()
            ));
        } catch (Exception e) {
            logger.error("Error rejecting request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Rejection failed",
                    "message", e.getMessage()
            ));
        }
    }

    // ========== Delete Endpoints ========== //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServiceRequest(@PathVariable Long id) {
        try {
            serviceRequestService.deleteServiceRequest(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Service request deleted successfully",
                    "requestId", id
            ));
        } catch (Exception e) {
            logger.error("Error deleting request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Deletion failed",
                    "message", e.getMessage()
            ));
        }
    }
}