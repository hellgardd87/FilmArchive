package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.CountryOption;
import com.hellgardd.filmarchive.entity.Film;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.repository.FilmRepository;
import com.hellgardd.filmarchive.repository.GenreRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchService {

 private final FilmRepository films;
 private final GenreRepository genres;

 public SearchService(
         FilmRepository films,
         GenreRepository genres
 ) {
  this.films = films;
  this.genres = genres;
 }

 private void checkGenre(Long genre) {
  if (genre == null || !genres.existsById(genre)) {
   throw new FormValidationException(
           "genre",
           "Выберите существующий жанр"
   );
  }
 }

 public List<CountryOption> countries(Long genre) {
  checkGenre(genre);

  return films.findCountriesByGenre(genre)
          .stream()
          .map(country ->
                  new CountryOption(
                          country.getId(),
                          country.getTitle()
                  )
          )
          .toList();
 }

 public List<Film> search(
         Long genre,
         Long country
 ) {
  checkGenre(genre);

  if (country != null
          && countries(genre)
          .stream()
          .noneMatch(option -> option.id().equals(country))) {

   throw new FormValidationException(
           "country",
           "В этой стране нет фильмов выбранного жанра"
   );
  }

  if (country == null) {
   return films.findByGenreIdOrderByTitleAsc(genre);
  }

  return films.findByGenreIdAndCountryIdOrderByTitleAsc(
          genre,
          country
  );
 }
}