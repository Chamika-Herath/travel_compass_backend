package com.travel.compass.repository;

import com.travel.compass.model.GuidePackage;
import com.travel.compass.model.HotelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HotelPackageRepository extends JpaRepository<HotelPackage, Long> {
    List<HotelPackage> findByHotelOwnerId(Long hotelOwnerId);
    @Query("SELECT hp FROM HotelPackage hp JOIN hp.locations l WHERE l.id = :locationId")
    List<HotelPackage> findByLocationId(@Param("locationId") Long locationId);


}