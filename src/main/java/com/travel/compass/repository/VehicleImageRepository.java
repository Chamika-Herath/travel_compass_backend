package com.travel.compass.repository;

import com.travel.compass.model.VehicleImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {
    List<VehicleImage> findByVehicle_Id(Long vehicleId); // Fetch images for a specific vehicle

    @Transactional
    void deleteByVehicle_Id(Long vehicleId);
}

