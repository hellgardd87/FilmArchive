package com.hellgardd.filmarchive.repository;

import com.hellgardd.filmarchive.entity.ShootingQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ShootingQualityRepository extends JpaRepository<ShootingQuality,Long> {
 boolean existsByTypeIgnoreCase(String value);
 List<ShootingQuality> findAllByOrderByTypeAsc();
}
