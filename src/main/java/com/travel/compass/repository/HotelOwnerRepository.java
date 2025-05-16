//package com.travel.compass.repository;
//
//import com.travel.compass.model.HotelOwner;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import java.util.Optional;
//
//public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {
//    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM HotelOwner h WHERE h.user.id = :userId")
//    boolean existsByUserId(@Param("userId") Long userId);
//
//    Optional<HotelOwner> findByUser_Id(Long userId);
//}




package com.travel.compass.repository;

import com.travel.compass.model.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {
    @Query("SELECT h FROM HotelOwner h WHERE h.user.id = :userId")
    Optional<HotelOwner> findByUser_Id(@Param("userId") Long userId);
}