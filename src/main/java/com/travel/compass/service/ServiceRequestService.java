//
//
//package com.travel.compass.service;
//
//import com.travel.compass.model.ServiceRequest;
//import com.travel.compass.repository.ServiceRequestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class ServiceRequestService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestService.class);
//
//    @Autowired
//    private ServiceRequestRepository serviceRequestRepository;
//
//
//
//
//    // ✅ Submit a new service request
//    public ServiceRequest submitRequest(ServiceRequest request) {
//        request.setStatus("PENDING"); // Default status
//        logger.info("Submitting new service request for user ID: {}", request.getUserId());
//        return serviceRequestRepository.save(request);
//    }
//
//    // ✅ Get all pending service requests
//    public List<ServiceRequest> getAllPendingRequests() {
//        logger.info("Fetching all pending service requests...");
//        return serviceRequestRepository.findByStatus("PENDING");
//    }
//
//    // ✅ Get all service requests (for admin dashboard)
//    public List<ServiceRequest> getAllRequests() {
//        logger.info("Fetching all service requests...");
//        return serviceRequestRepository.findAll();
//    }
//
//    // ✅ Update the request status (approve/reject)
//    public ServiceRequest updateRequestStatus(Long id, String status) {
//        logger.info("Updating status of request ID: {} to {}", id, status);
//        Optional<ServiceRequest> optionalRequest = serviceRequestRepository.findById(id);
//        if (optionalRequest.isPresent()) {
//            ServiceRequest request = optionalRequest.get();
//            request.setStatus(status);
//            return serviceRequestRepository.save(request);
//        } else {
//            throw new RuntimeException("Service request not found for ID: " + id);
//        }
//    }
//}
//


package com.travel.compass.service;

import com.travel.compass.model.ServiceRequest;
import com.travel.compass.model.User;
import com.travel.compass.repository.ServiceRequestRepository;
import com.travel.compass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ServiceRequestService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestService.class);

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private UserRepository userRepository; // Added UserRepository to update user role

    // ✅ Submit a new service request
    public ServiceRequest submitRequest(ServiceRequest request) {
        request.setStatus("PENDING"); // Default status
        logger.info("Submitting new service request for user: {}", request.getUser().getId());
        return serviceRequestRepository.save(request);
    }

    // ✅ Get all pending service requests
    public List<ServiceRequest> getAllPendingRequests() {
        logger.info("Fetching all pending service requests...");
        return serviceRequestRepository.findByStatus("PENDING");
    }

    // ✅ Get all service requests (for admin dashboard)
    public List<ServiceRequest> getAllRequests() {
        logger.info("Fetching all service requests...");
        return serviceRequestRepository.findAll();
    }

    // ✅ Approve or Reject a request & update user role if approved
    public ServiceRequest updateRequestStatus(Long requestId, String status) {
        logger.info("Updating status of request ID: {} to {}", requestId, status);
        Optional<ServiceRequest> optionalRequest = serviceRequestRepository.findById(requestId);

        if (optionalRequest.isEmpty()) {
            throw new RuntimeException("Service request not found for ID: " + requestId);
        }

        ServiceRequest request = optionalRequest.get();
        request.setStatus(status.toUpperCase()); // ✅ Set new status

        if (status.equalsIgnoreCase("APPROVED")) {
            User user = request.getUser();
            String newRole = determineUserRole(request.getServiceType());
            user.setRole(newRole);
            userRepository.save(user); // ✅ Save updated user role
        }

        return serviceRequestRepository.save(request);
    }

    // ✅ Determine user role based on service type
    private String determineUserRole(String serviceType) {
        return switch (serviceType.toUpperCase()) {
            case "GUIDE" -> "ROLE_GUIDE";
            case "HOTEL_PROVIDER" -> "ROLE_HOTEL_OWNER";
            case "VEHICLE_PROVIDER" -> "ROLE_VEHICLE_OWNER";
            default -> "ROLE_USER";
        };
    }
}
