//package com.travel.compass.repository;
//
//import com.travel.compass.model.Guide;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
//public interface GuideRepository extends JpaRepository<Guide, Long> {
//    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Guide g WHERE g.user.id = :userId")
//    boolean existsByUserId(@Param("userId") Long userId);
//
//    Optional<Guide> findByUser_Id(Long userId);
//}





package com.travel.compass.repository;

import com.travel.compass.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    // Check if a guide exists for a given user ID
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Guide g WHERE g.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    // Find guide by user ID
    Optional<Guide> findByUser_Id(Long userId);

    // Get all guides (inherited from JpaRepository, no extra method needed)
    List<Guide> findAll();
}
