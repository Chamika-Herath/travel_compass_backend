package com.travel.compass.repository;

import com.travel.compass.model.HotelOwner;
import com.travel.compass.model.VehicleProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {
   // @Query("SELECT h FROM HotelOwner h WHERE h.user.id = :userId")
   // Optional<HotelOwner> findByUser_Id(@Param("userId") Long userId);

    @Query("SELECT h FROM HotelOwner h WHERE h.user.id = :userId")
    Optional<HotelOwner> findByUser_Id(@Param("userId") Long userId);


    List<HotelOwner> findAll();
}