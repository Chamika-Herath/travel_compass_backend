// VehiclePackageRepository.java (new)
package com.travel.compass.repository;

import com.travel.compass.model.VehiclePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VehiclePackageRepository extends JpaRepository<VehiclePackage, Long> {
    List<VehiclePackage> findByVehicleProviderId(Long providerId);

    @Query("SELECT vp FROM VehiclePackage vp JOIN vp.locations l WHERE l.id = :locationId")
    List<VehiclePackage> findByLocationId(@Param("locationId") Long locationId);
}