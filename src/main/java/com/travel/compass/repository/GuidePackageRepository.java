package com.travel.compass.repository;





import com.travel.compass.model.GuidePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuidePackageRepository extends JpaRepository<GuidePackage, Long> {
    List<GuidePackage> findByGuideId(Long guideId);
}


