package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.entity.AgeLimit;
import com.hellgardd.filmarchive.entity.Country;
import com.hellgardd.filmarchive.entity.Film;
import com.hellgardd.filmarchive.entity.Genre;
import com.hellgardd.filmarchive.entity.Person;
import com.hellgardd.filmarchive.entity.ShootingQuality;

import com.hellgardd.filmarchive.repository.AgeLimitRepository;
import com.hellgardd.filmarchive.repository.CountryRepository;
import com.hellgardd.filmarchive.repository.FilmRepository;
import com.hellgardd.filmarchive.repository.GenreRepository;
import com.hellgardd.filmarchive.repository.PersonRepository;
import com.hellgardd.filmarchive.repository.ShootingQualityRepository;

import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseInitializationService {

 private final EntityManager em;
 private final FilmRepository films;
 private final GenreRepository genres;
 private final CountryRepository countries;
 private final AgeLimitRepository ages;
 private final ShootingQualityRepository qualities;
 private final PersonRepository people;
 private final boolean seed;

 public DatabaseInitializationService(
         EntityManager em,
         FilmRepository films,
         GenreRepository genres,
         CountryRepository countries,
         AgeLimitRepository ages,
         ShootingQualityRepository qualities,
         PersonRepository people,
         @Value("${app.seed-data:true}") boolean seed
 ) {
  this.em = em;
  this.films = films;
  this.genres = genres;
  this.countries = countries;
  this.ages = ages;
  this.qualities = qualities;
  this.people = people;
  this.seed = seed;
 }

 @Transactional
 public void initialize() {

  // PostgreSQL expression indexes protect against
  // concurrent case-insensitive duplicate inserts.
  for (String[] index : List.of(
          new String[]{"films", "title"},
          new String[]{"genres", "title"},
          new String[]{"genres", "code"},
          new String[]{"countries", "title"},
          new String[]{"countries", "code"},
          new String[]{"age_limits", "category"},
          new String[]{"age_limits", "code"},
          new String[]{"shooting_qualities", "type"},
          new String[]{"people", "full_name"}
  )) {
   em.createNativeQuery(
           "CREATE UNIQUE INDEX IF NOT EXISTS ux_"
                   + index[0]
                   + "_"
                   + index[1]
                   + "_ci ON "
                   + index[0]
                   + " (lower("
                   + index[1]
                   + "))"
   ).executeUpdate();
  }

  // Demo data are inserted only into a completely empty catalog.
  if (!seed
          || films.count()
          + genres.count()
          + countries.count()
          + ages.count()
          + qualities.count()
          + people.count() > 0) {
   return;
  }

  Genre sci = genres.save(
          new Genre("Фантастика", "SCI")
  );

  Genre comedy = genres.save(
          new Genre("Комедия", "COM")
  );

  Genre drama = genres.save(
          new Genre("Драма", "DRA")
  );

  genres.save(
          new Genre("Боевик", "ACT")
  );

  Country usa = countries.save(
          new Country("США", "US")
  );

  Country uk = countries.save(
          new Country("Великобритания", "GB")
  );

  countries.save(
          new Country("Франция", "FR")
  );

  countries.save(
          new Country("Германия", "DE")
  );

  ages.save(
          new AgeLimit("0+", "0")
  );

  AgeLimit a6 = ages.save(
          new AgeLimit("6+", "6")
  );

  AgeLimit a12 = ages.save(
          new AgeLimit("12+", "12")
  );

  AgeLimit a16 = ages.save(
          new AgeLimit("16+", "16")
  );

  ages.save(
          new AgeLimit("18+", "18")
  );

  qualities.save(
          new ShootingQuality("HD")
  );

  ShootingQuality full = qualities.save(
          new ShootingQuality("Full HD")
  );

  ShootingQuality four = qualities.save(
          new ShootingQuality("4K")
  );

  Person keanu = people.save(
          new Person("Киану Ривз")
  );

  Person laurence = people.save(
          new Person("Лоренс Фишборн")
  );

  Person carrie = people.save(
          new Person("Кэрри-Энн Мосс")
  );

  Person matthew = people.save(
          new Person("Мэттью Макконахи")
  );

  Person anne = people.save(
          new Person("Энн Хэтэуэй")
  );

  Person tom = people.save(
          new Person("Том Хэнкс")
  );

  Person hugh = people.save(
          new Person("Хью Грант")
  );

  Person sally = people.save(
          new Person("Салли Хокинс")
  );

  addFilm(
          "Матрица",
          "Хакер Нео узнаёт, что привычный мир — компьютерная симуляция.",
          136,
          "63000000",
          "467222728",
          "1999-03-31",
          sci,
          usa,
          a16,
          four,
          keanu,
          laurence,
          carrie
  );

  addFilm(
          "Интерстеллар",
          "Команда исследователей отправляется в космос, чтобы найти новый дом для человечества.",
          169,
          "165000000",
          "701729206",
          "2014-11-06",
          sci,
          uk,
          a12,
          four,
          matthew,
          anne
  );

  addFilm(
          "Форрест Гамп",
          "История доброго человека, оказавшегося в центре событий своей эпохи.",
          142,
          "55000000",
          "678226465",
          "1994-07-06",
          drama,
          usa,
          a12,
          full,
          tom
  );

  addFilm(
          "Приключения Паддингтона 2",
          "Медвежонок ищет подарок для тёти и попадает в неожиданное приключение.",
          103,
          "40000000",
          "227992487",
          "2017-11-10",
          comedy,
          uk,
          a6,
          full,
          hugh,
          sally
  );
 }

 private void addFilm(
         String title,
         String description,
         int duration,
         String budget,
         String box,
         String date,
         Genre genre,
         Country country,
         AgeLimit age,
         ShootingQuality quality,
         Person... actors
 ) {
  Film film = new Film();

  film.setTitle(title);
  film.setDescription(description);
  film.setDuration(duration);
  film.setBudget(new BigDecimal(budget));
  film.setTotalBoxOffice(new BigDecimal(box));
  film.setReleaseDate(LocalDate.parse(date));

  film.setGenre(genre);
  film.setCountry(country);
  film.setAgeLimit(age);
  film.setShootingQuality(quality);

  film.getActors().addAll(
          Arrays.asList(actors)
  );

  films.save(film);
 }
}