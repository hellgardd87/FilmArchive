package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.Statistics;
import com.hellgardd.filmarchive.repository.AgeLimitRepository;
import com.hellgardd.filmarchive.repository.CountryRepository;
import com.hellgardd.filmarchive.repository.FilmRepository;
import com.hellgardd.filmarchive.repository.GenreRepository;
import com.hellgardd.filmarchive.repository.PersonRepository;
import com.hellgardd.filmarchive.repository.ShootingQualityRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {

 private final FilmRepository films;
 private final PersonRepository people;
 private final GenreRepository genres;
 private final CountryRepository countries;
 private final AgeLimitRepository ages;
 private final ShootingQualityRepository qualities;

 public StatisticsService(
         FilmRepository films,
         PersonRepository people,
         GenreRepository genres,
         CountryRepository countries,
         AgeLimitRepository ages,
         ShootingQualityRepository qualities
 ) {
  this.films = films;
  this.people = people;
  this.genres = genres;
  this.countries = countries;
  this.ages = ages;
  this.qualities = qualities;
 }

 @Transactional(
         readOnly = true,
         isolation = Isolation.REPEATABLE_READ
 )
 public Statistics get() {
  return new Statistics(
          films.count(),
          people.count(),
          genres.count(),
          countries.count(),
          ages.count(),
          qualities.count(),
          films.countActorLinks()
  );
 }
}