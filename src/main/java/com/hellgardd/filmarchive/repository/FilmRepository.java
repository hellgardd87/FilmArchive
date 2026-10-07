package com.hellgardd.filmarchive.repository;

import com.hellgardd.filmarchive.entity.Country;
import com.hellgardd.filmarchive.entity.Film;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmRepository extends JpaRepository<Film, Long> {

 boolean existsByTitleIgnoreCase(String title);

 boolean existsByTitleIgnoreCaseAndIdNot(
         String title,
         Long id
 );

 @EntityGraph(attributePaths = {
         "genre",
         "country",
         "ageLimit",
         "shootingQuality",
         "actors"
 })
 List<Film> findAllByOrderByTitleAsc();

 @EntityGraph(attributePaths = {
         "genre",
         "country",
         "ageLimit",
         "shootingQuality",
         "actors"
 })
 Optional<Film> findById(Long id);

 @EntityGraph(attributePaths = {
         "genre",
         "country",
         "ageLimit",
         "shootingQuality",
         "actors"
 })
 List<Film> findByGenreIdOrderByTitleAsc(
         Long genreId
 );

 @EntityGraph(attributePaths = {
         "genre",
         "country",
         "ageLimit",
         "shootingQuality",
         "actors"
 })
 List<Film> findByGenreIdAndCountryIdOrderByTitleAsc(
         Long genreId,
         Long countryId
 );

 @EntityGraph(attributePaths = {
         "actors"
 })
 List<Film> findByActorsId(
         Long personId
 );

 @Query("""
            select distinct f.country
            from Film f
            where f.genre.id = :genreId
            order by f.country.title
            """)
 List<Country> findCountriesByGenre(
         @Param("genreId") Long genreId
 );

 @Query("""
            select count(p)
            from Film f
            join f.actors p
            """)
 long countActorLinks();
}