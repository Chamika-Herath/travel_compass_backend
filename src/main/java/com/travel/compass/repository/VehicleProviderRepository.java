package com.travel.compass.repository;

import com.travel.compass.model.Guide;
import com.travel.compass.model.VehicleProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleProviderRepository extends JpaRepository<VehicleProvider, Long> {

    @Query("SELECT vp FROM VehicleProvider vp WHERE vp.user.id = :userId")
    Optional<VehicleProvider> findByUser_Id(@Param("userId") Long userId);


    List<VehicleProvider> findAll();
}