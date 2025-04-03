package com.travel.compass.repository;

import com.travel.compass.model.HotelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelPackageRepository extends JpaRepository<HotelPackage, Long> {
}