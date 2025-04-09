package com.travel.compass.repository;

import com.travel.compass.model.VehicleProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface VehicleProviderRepository extends JpaRepository<VehicleProvider, Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM VehicleProvider v WHERE v.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    Optional<VehicleProvider> findByUser_Id(Long userId);
}