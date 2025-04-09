package com.travel.compass.service;

import com.travel.compass.model.*;
import com.travel.compass.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestService.class);

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final GuideRepository guideRepository;
    private final HotelOwnerRepository hotelOwnerRepository;
    private final VehicleProviderRepository vehicleProviderRepository;

    // ========== CRUD Operations ========== //

    @Transactional
    public ServiceRequest createServiceRequest(ServiceRequest request) {
        request.setStatus("PENDING");
        logger.info("Creating service request for user ID: {}", request.getUser().getId());
        return serviceRequestRepository.save(request);
    }

    public Optional<ServiceRequest> getServiceRequestById(Long id) {
        logger.debug("Fetching service request ID: {}", id);
        return serviceRequestRepository.findById(id);
    }

    public List<ServiceRequest> getAllServiceRequests() {
        logger.debug("Fetching all service requests");
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> getAllPendingServiceRequests() {
        logger.debug("Fetching pending service requests");
        return serviceRequestRepository.findByStatus("PENDING");
    }

    @Transactional
    public ServiceRequest updateServiceRequest(ServiceRequest request) {
        logger.info("Updating service request ID: {}", request.getId());
        return serviceRequestRepository.save(request);
    }

    @Transactional
    public void deleteServiceRequest(Long id) {
        logger.info("Deleting service request ID: {}", id);
        if (!serviceRequestRepository.existsById(id)) {
            throw new RuntimeException("Service request not found with ID: " + id);
        }
        serviceRequestRepository.deleteById(id);
    }

    // ========== Business Logic Methods ========== //

    public List<ServiceRequest> getServiceRequestsByUserId(Long userId) {
        logger.debug("Fetching requests for user ID: {}", userId);
        return serviceRequestRepository.findByUser_Id(userId);
    }

    @Transactional
    public ServiceRequest approveServiceRequest(Long requestId) {
        return updateRequestStatus(requestId, "APPROVED");
    }

    @Transactional
    public ServiceRequest rejectServiceRequest(Long requestId) {
        return updateRequestStatus(requestId, "REJECTED");
    }

    // ========== Helper Methods ========== //

    @Transactional
    protected ServiceRequest updateRequestStatus(Long requestId, String status) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found"));

        request.setStatus(status);

        if ("APPROVED".equalsIgnoreCase(status)) {
            User user = request.getUser();
            String newRole = determineUserRole(request.getServiceType());
            user.setRole(newRole);
            userRepository.save(user);
            createRoleSpecificProfile(user, request);
        }

        return serviceRequestRepository.save(request);
    }

    private void createRoleSpecificProfile(User user, ServiceRequest request) {
        switch (user.getRole()) {
            case "ROLE_GUIDE":
                createGuideProfile(user, request);
                break;
            case "ROLE_HOTEL_OWNER":
                createHotelOwnerProfile(user, request);
                break;
            case "ROLE_VEHICLE_OWNER":
                createVehicleProviderProfile(user, request);
                break;
        }
    }

    private void createGuideProfile(User user, ServiceRequest request) {
        if (guideRepository.findByUser_Id(user.getId()).isPresent()) return;

        Guide guide = new Guide();
        guide.setUser(user);
        guide.setLicenseNumber(request.getLicenseNumber());
        guide.setAreasOfExpertise(request.getDescription());
        guide.setLanguagesSpoken("English");
        guide.setDescription(request.getDescription());
        guideRepository.save(guide);
    }

    private void createHotelOwnerProfile(User user, ServiceRequest request) {
        if (hotelOwnerRepository.findByUser_Id(user.getId()).isPresent()) return;

        HotelOwner owner = new HotelOwner();
        owner.setUser(user);
        owner.setHotelName(request.getBusinessName());
        owner.setHotelAddress(request.getAddress());
        owner.setBusinessLicense(request.getLicenseNumber());
        hotelOwnerRepository.save(owner);
    }

    private void createVehicleProviderProfile(User user, ServiceRequest request) {
        if (vehicleProviderRepository.findByUser_Id(user.getId()).isPresent()) return;

        VehicleProvider provider = new VehicleProvider();
        provider.setUser(user);
        provider.setCompanyName(request.getBusinessName());
        provider.setVehicleTypes(request.getVehicleTypes());
        provider.setLicenseNumber(request.getLicenseNumber());
        vehicleProviderRepository.save(provider);
    }

    private String determineUserRole(String serviceType) {
        return switch (serviceType.toUpperCase()) {
            case "GUIDE" -> "ROLE_GUIDE";
            case "HOTEL_PROVIDER", "HOTEL_OWNER" -> "ROLE_HOTEL_OWNER";
            case "VEHICLE_PROVIDER", "DRIVER" -> "ROLE_VEHICLE_OWNER";
            default -> "ROLE_USER";
        };
    }
}