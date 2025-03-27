package com.travel.compass.service;

import com.travel.compass.model.ServiceRequest;
import com.travel.compass.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequest submitRequest(ServiceRequest request) {
        request.setStatus("PENDING"); // Default status
        return serviceRequestRepository.save(request);
    }

    public List<ServiceRequest> getPendingRequests() {
        return serviceRequestRepository.findByStatus("PENDING");
    }

    public ServiceRequest approveRequest(Long id) {
        ServiceRequest request = serviceRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("APPROVED");
        return serviceRequestRepository.save(request);
    }

    public ServiceRequest rejectRequest(Long id) {
        ServiceRequest request = serviceRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("REJECTED");
        return serviceRequestRepository.save(request);
    }
}
