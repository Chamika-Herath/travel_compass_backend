





package com.travel.compass.repository;

import com.travel.compass.model.GuidePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//public interface GuidePackageRepository extends JpaRepository<GuidePackage, Long> {
//    List<GuidePackage> findByGuideId(Long guideId);
//
//    @Query("SELECT gp FROM GuidePackage gp JOIN gp.locations l WHERE l.id = :locationId")
//    List<GuidePackage> findByLocationId(@Param("locationId") Long locationId);
//}


public interface GuidePackageRepository extends JpaRepository<GuidePackage, Long> {
    List<GuidePackage> findByGuideId(Long guideId);
    @Query("SELECT gp FROM GuidePackage gp JOIN gp.locations l WHERE l.id = :locationId")
    List<GuidePackage> findByLocationId(@Param("locationId") Long locationId);
}

