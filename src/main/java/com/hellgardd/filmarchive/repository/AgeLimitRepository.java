package com.hellgardd.filmarchive.repository;

import com.hellgardd.filmarchive.entity.AgeLimit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgeLimitRepository extends JpaRepository<AgeLimit, Long> {
 boolean existsByCategoryIgnoreCase(String value);
 boolean existsByCodeIgnoreCase(String value);
 List<AgeLimit> findAllByOrderByCategoryAsc();
}