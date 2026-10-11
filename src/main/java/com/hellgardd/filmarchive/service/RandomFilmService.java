package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.entity.Film;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class RandomFilmService {
 private final FilmService films;
 public RandomFilmService(FilmService films) { this.films = films; }
 public Optional<Film> random() {
  var all = films.all();
  return all.isEmpty() ? Optional.empty() : Optional.of(all.get(ThreadLocalRandom.current().nextInt(all.size())));
 }
}
