package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.FilmForm;
import com.hellgardd.filmarchive.entity.Film;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.exception.NotFoundException;
import com.hellgardd.filmarchive.repository.AgeLimitRepository;
import com.hellgardd.filmarchive.repository.CountryRepository;
import com.hellgardd.filmarchive.repository.FilmRepository;
import com.hellgardd.filmarchive.repository.GenreRepository;
import com.hellgardd.filmarchive.repository.PersonRepository;
import com.hellgardd.filmarchive.repository.ShootingQualityRepository;

import jakarta.validation.Validator;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FilmService {

 private final FilmRepository films;
 private final GenreRepository genres;
 private final CountryRepository countries;
 private final AgeLimitRepository ages;
 private final ShootingQualityRepository qualities;
 private final PersonRepository people;
 private final Clock clock;
 private final Validator validator;
 private final TextNormalizer text;

 public FilmService(
         FilmRepository films,
         GenreRepository genres,
         CountryRepository countries,
         AgeLimitRepository ages,
         ShootingQualityRepository qualities,
         PersonRepository people,
         Clock clock,
         Validator validator,
         TextNormalizer text
 ) {
  this.films = films;
  this.genres = genres;
  this.countries = countries;
  this.ages = ages;
  this.qualities = qualities;
  this.people = people;
  this.clock = clock;
  this.validator = validator;
  this.text = text;
 }

 public List<Film> all() {
  return films.findAllByOrderByTitleAsc();
 }

 public Film get(Long id) {
  return films.findById(id)
          .orElseThrow(
                  () -> new NotFoundException("Фильм не найден")
          );
 }

 public FilmForm form(Long id) {
  return new FilmForm(get(id));
 }

 @Transactional
 public void add(FilmForm form) {
  save(new Film(), form);
 }

 @Transactional
 public void update(
         Long id,
         FilmForm form
 ) {
  save(get(id), form);
 }

 private void save(
         Film film,
         FilmForm form
 ) {
  var violations = validator.validate(form);

  if (!violations.isEmpty()) {
   var violation = violations.iterator().next();

   throw new FormValidationException(
           violation.getPropertyPath().toString(),
           violation.getMessage()
   );
  }

  String title = text.normalize(form.getTitle());

  if (title.isBlank()) {
   throw new FormValidationException(
           "title",
           "Название не должно состоять из пробелов"
   );
  }

  if (form.getReleaseDate().isAfter(LocalDate.now(clock))) {
   throw new FormValidationException(
           "releaseDate",
           "Дата выхода не должна быть в будущем"
   );
  }

  boolean duplicate;

  if (film.getId() == null) {
   duplicate = films.existsByTitleIgnoreCase(title);
  } else {
   duplicate = films.existsByTitleIgnoreCaseAndIdNot(
           title,
           film.getId()
   );
  }

  if (duplicate) {
   throw new FormValidationException(
           "title",
           "Фильм с таким названием уже существует"
   );
  }

  film.setGenre(
          genres.findById(form.getGenreId())
                  .orElseThrow(
                          () -> new FormValidationException(
                                  "genreId",
                                  "Выбранный жанр не найден"
                          )
                  )
  );

  film.setCountry(
          countries.findById(form.getCountryId())
                  .orElseThrow(
                          () -> new FormValidationException(
                                  "countryId",
                                  "Выбранная страна не найдена"
                          )
                  )
  );

  film.setAgeLimit(
          ages.findById(form.getAgeLimitId())
                  .orElseThrow(
                          () -> new FormValidationException(
                                  "ageLimitId",
                                  "Возрастное ограничение не найдено"
                          )
                  )
  );

  film.setShootingQuality(
          qualities.findById(form.getShootingQualityId())
                  .orElseThrow(
                          () -> new FormValidationException(
                                  "shootingQualityId",
                                  "Качество не найдено"
                          )
                  )
  );

  var actors = people.findAllById(form.getActorIds());

  if (actors.size() != form.getActorIds().size()) {
   throw new FormValidationException(
           "actorIds",
           "Один из выбранных актёров больше не существует"
   );
  }

  film.setTitle(title);

  film.setDescription(
          form.getDescription() == null
                  ? ""
                  : form.getDescription().strip()
  );

  film.setDuration(form.getDuration());
  film.setBudget(form.getBudget());
  film.setTotalBoxOffice(form.getTotalBoxOffice());
  film.setReleaseDate(form.getReleaseDate());

  film.getActors().clear();
  film.getActors().addAll(actors);

  films.saveAndFlush(film);
 }

 @Transactional
 public void delete(Long id) {
  Film film = get(id);

  film.getActors().clear();
  films.flush();

  films.delete(film);
  films.flush();
 }
}