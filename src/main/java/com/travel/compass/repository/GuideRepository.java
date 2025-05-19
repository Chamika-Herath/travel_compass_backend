package com.travel.compass.repository;

import com.travel.compass.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    @Query("SELECT g FROM Guide g WHERE g.user.id = :userId")
    Optional<Guide> findByUser_Id(@Param("userId") Long userId);

    List<Guide> findAll();
}