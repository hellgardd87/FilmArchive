package com.hellgardd.filmarchive.repository;

import com.hellgardd.filmarchive.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PersonRepository extends JpaRepository<Person,Long> {
 boolean existsByFullNameIgnoreCase(String value);
 List<Person> findAllByOrderByFullNameAsc();
}
