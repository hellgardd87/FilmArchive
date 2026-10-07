package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.dto.*;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.service.*;
import jakarta.validation.Valid;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogController {

 private final FilmService films;
 private final GenreService genres;
 private final CountryService countries;
 private final PersonService people;
 private final ReferenceService references;
 private final SearchService search;
 private final StatisticsService statistics;
 private final Clock clock;

 public CatalogController(
         FilmService films,
         GenreService genres,
         CountryService countries,
         PersonService people,
         ReferenceService references,
         SearchService search,
         StatisticsService statistics,
         Clock clock
 ) {
  this.films = films;
  this.genres = genres;
  this.countries = countries;
  this.people = people;
  this.references = references;
  this.search = search;
  this.statistics = statistics;
  this.clock = clock;
 }

 @GetMapping("/")
 public String index(Model model) {
  model.addAttribute("stats", statistics.get());
  return "index";
 }

 private void options(Model model) {
  model.addAttribute("genres", genres.all());
  model.addAttribute("countries", countries.all());
  model.addAttribute("ageLimits", references.ages());
  model.addAttribute("qualities", references.qualities());
  model.addAttribute("people", people.all());
  model.addAttribute("today", LocalDate.now(clock));
 }

 private String editPage(Model model) {
  options(model);

  model.addAttribute("films", films.all());

  if (!model.containsAttribute("filmForm")) {
   model.addAttribute("filmForm", new FilmForm());
  }

  if (!model.containsAttribute("genreForm")) {
   model.addAttribute("genreForm", new ReferenceForm());
  }

  if (!model.containsAttribute("countryForm")) {
   model.addAttribute("countryForm", new ReferenceForm());
  }

  if (!model.containsAttribute("ageForm")) {
   model.addAttribute("ageForm", new AgeLimitForm());
  }

  if (!model.containsAttribute("qualityForm")) {
   model.addAttribute("qualityForm", new QualityForm());
  }

  if (!model.containsAttribute("personForm")) {
   model.addAttribute("personForm", new PersonForm());
  }

  return "edit";
 }

 @GetMapping("/edit")
 public String edit(Model model) {
  return editPage(model);
 }

 private void errors(
         BindingResult result,
         FormValidationException ex
 ) {
  ex.getErrors().forEach(
          (field, message) ->
                  result.rejectValue(field, "invalid", message)
  );
 }

 private String success(
         RedirectAttributes redirect,
         String message
 ) {
  redirect.addFlashAttribute("message", message);
  redirect.addFlashAttribute("messageType", "success");

  return "redirect:/edit";
 }

 private void warning(Model model) {
  model.addAttribute("message", "Проверьте поля формы");
  model.addAttribute("messageType", "warning");
 }

 @PostMapping("/add_genre")
 public String addGenre(
         @Valid @ModelAttribute("genreForm") ReferenceForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    genres.add(form);
    return success(redirect, "Жанр успешно добавлен");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_country")
 public String addCountry(
         @Valid @ModelAttribute("countryForm") ReferenceForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    countries.add(form);
    return success(redirect, "Страна успешно добавлена");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_quality")
 public String addQuality(
         @Valid @ModelAttribute("qualityForm") QualityForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    references.addQuality(form);
    return success(redirect, "Качество успешно добавлено");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_age_limit")
 public String addAge(
         @Valid @ModelAttribute("ageForm") AgeLimitForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    references.addAge(form);
    return success(
            redirect,
            "Возрастное ограничение успешно добавлено"
    );
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_person")
 public String addPerson(
         @Valid @ModelAttribute("personForm") PersonForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    people.add(form);
    return success(redirect, "Актёр успешно добавлен");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/delete_person/{id}")
 public String deletePerson(
         @PathVariable Long id,
         RedirectAttributes redirect
 ) {
  people.delete(id);

  return success(
          redirect,
          "Актёр и его связи успешно удалены"
  );
 }

 @PostMapping("/add_film")
 public String addFilm(
         @Valid @ModelAttribute("filmForm") FilmForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    films.add(form);
    return success(redirect, "Фильм успешно добавлен");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @GetMapping("/edit_film/{id}")
 public String editFilm(
         @PathVariable Long id,
         Model model
 ) {
  model.addAttribute("filmForm", films.form(id));
  model.addAttribute("filmId", id);

  options(model);

  return "edit-film";
 }

 @PostMapping("/update_film/{id}")
 public String updateFilm(
         @PathVariable Long id,
         @Valid @ModelAttribute("filmForm") FilmForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  films.get(id);

  if (!result.hasErrors()) {
   try {
    films.update(id, form);
    return success(redirect, "Фильм успешно обновлён");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  model.addAttribute("filmId", id);
  options(model);
  warning(model);

  return "edit-film";
 }

 @PostMapping("/delete_film/{id}")
 public String deleteFilm(
         @PathVariable Long id,
         RedirectAttributes redirect
 ) {
  films.delete(id);

  return success(
          redirect,
          "Фильм успешно удалён"
  );
 }

 @GetMapping("/search")
 public String search(
         @RequestParam(required = false) Long genre,
         @RequestParam(required = false) Long country,
         Model model
 ) {
  model.addAttribute("genres", genres.all());
  model.addAttribute("selectedGenre", genre);
  model.addAttribute("selectedCountry", country);

  model.addAttribute("countryOptions", List.of());
  model.addAttribute("results", List.of());
  model.addAttribute("searched", genre != null);

  try {
   if (genre != null) {
    model.addAttribute(
            "countryOptions",
            search.countries(genre)
    );

    model.addAttribute(
            "results",
            search.search(genre, country)
    );
   } else if (country != null) {
    throw new FormValidationException(
            "genre",
            "Сначала выберите жанр"
    );
   }
  } catch (FormValidationException ex) {
   model.addAttribute("message", ex.getMessage());
   model.addAttribute("messageType", "warning");
  }

  return "search";
 }

 @GetMapping("/get_countries")
 @ResponseBody
 public List<CountryOption> getCountries(
         @RequestParam Long genre
 ) {
  return search.countries(genre);
 }

 @GetMapping("/aggregate")
 public String aggregate(Model model) {
  model.addAttribute("stats", statistics.get());
  return "aggregate";
 }

 @GetMapping("/info")
 public String info() {
  return "info";
 }
}