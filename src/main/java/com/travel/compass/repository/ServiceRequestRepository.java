package com.travel.compass.repository;

import com.travel.compass.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByStatus(String status);
    List<ServiceRequest> findByUser_Id(Long userId);
    Optional<ServiceRequest> findByIdAndUser_Id(Long id, Long userId);
}