package com.hellgardd.filmarchive.repository;

import com.hellgardd.filmarchive.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface GenreRepository extends JpaRepository<Genre,Long> {
 boolean existsByTitleIgnoreCase(String value);
 boolean existsByCodeIgnoreCase(String value);
 List<Genre> findAllByOrderByTitleAsc();
}
