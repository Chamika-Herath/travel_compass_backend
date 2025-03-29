package com.travel.compass.controller;

import com.travel.compass.model.ServiceRequest;
import com.travel.compass.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    // Get all pending service requests
    @GetMapping("/service-requests")
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequests() {
        return ResponseEntity.ok(serviceRequestService.getAllPendingRequests());
    }

    // Approve service request
    @PostMapping("/service-requests/{id}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long id) {
        serviceRequestService.updateRequestStatus(id, "APPROVED");
        return ResponseEntity.ok("Service request approved successfully.");
    }

    // Reject service request
    @PostMapping("/service-requests/{id}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {
        serviceRequestService.updateRequestStatus(id, "REJECTED");
        return ResponseEntity.ok("Service request rejected successfully.");
    }
}
