package com.hellgardd.filmarchive.repository;

import com.hellgardd.filmarchive.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CountryRepository extends JpaRepository<Country,Long> {
 boolean existsByTitleIgnoreCase(String value);
 boolean existsByCodeIgnoreCase(String value);
 List<Country> findAllByOrderByTitleAsc();
}
