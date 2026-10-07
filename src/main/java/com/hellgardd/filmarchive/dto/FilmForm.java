package com.hellgardd.filmarchive.dto;

import com.hellgardd.filmarchive.entity.Film;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

public class FilmForm {

 @NotBlank(message = "Укажите название фильма")
 @Size(
         max = 200,
         message = "Название: не более 200 символов"
 )
 private String title;

 @Size(
         max = 5000,
         message = "Описание: не более 5000 символов"
 )
 private String description;

 @NotNull(message = "Укажите длительность")
 @Min(
         value = 1,
         message = "Длительность должна быть больше 0"
 )
 @Max(
         value = 300,
         message = "Длительность: не более 300 минут"
 )
 private Integer duration;

 @NotNull(message = "Укажите бюджет")
 @DecimalMin(
         value = "0",
         inclusive = false,
         message = "Бюджет должен быть больше 0"
 )
 @DecimalMax(
         value = "600000000",
         inclusive = false,
         message = "Бюджет должен быть меньше 600000000"
 )
 @Digits(
         integer = 9,
         fraction = 2,
         message = "Бюджет: не более двух знаков после запятой"
 )
 private BigDecimal budget;

 @NotNull(message = "Укажите кассовые сборы")
 @DecimalMin(
         value = "0",
         inclusive = false,
         message = "Сборы должны быть больше 0"
 )
 @DecimalMax(
         value = "3000000000",
         inclusive = false,
         message = "Сборы должны быть меньше 3000000000"
 )
 @Digits(
         integer = 10,
         fraction = 2,
         message = "Сборы: не более двух знаков после запятой"
 )
 private BigDecimal totalBoxOffice;

 @NotNull(message = "Укажите дату выхода")
 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
 private LocalDate releaseDate;

 @NotNull(message = "Выберите жанр")
 @Positive
 private Long genreId;

 @NotNull(message = "Выберите страну")
 @Positive
 private Long countryId;

 @NotNull(message = "Выберите возрастное ограничение")
 @Positive
 private Long ageLimitId;

 @NotNull(message = "Выберите качество")
 @Positive
 private Long shootingQualityId;

 @NotEmpty(message = "Выберите хотя бы одного актёра")
 private Set<@NotNull @Positive Long> actorIds = new LinkedHashSet<>();

 public FilmForm() {
 }

 public FilmForm(Film film) {
  this.title = film.getTitle();
  this.description = film.getDescription();
  this.duration = film.getDuration();
  this.budget = film.getBudget();
  this.totalBoxOffice = film.getTotalBoxOffice();
  this.releaseDate = film.getReleaseDate();

  this.genreId = film.getGenre().getId();
  this.countryId = film.getCountry().getId();
  this.ageLimitId = film.getAgeLimit().getId();
  this.shootingQualityId = film.getShootingQuality().getId();

  film.getActors()
          .forEach(person -> actorIds.add(person.getId()));
 }

 public String getTitle() {
  return title;
 }

 public void setTitle(String title) {
  this.title = title;
 }

 public String getDescription() {
  return description;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public Integer getDuration() {
  return duration;
 }

 public void setDuration(Integer duration) {
  this.duration = duration;
 }

 public BigDecimal getBudget() {
  return budget;
 }

 public void setBudget(BigDecimal budget) {
  this.budget = budget;
 }

 public BigDecimal getTotalBoxOffice() {
  return totalBoxOffice;
 }

 public void setTotalBoxOffice(BigDecimal totalBoxOffice) {
  this.totalBoxOffice = totalBoxOffice;
 }

 public LocalDate getReleaseDate() {
  return releaseDate;
 }

 public void setReleaseDate(LocalDate releaseDate) {
  this.releaseDate = releaseDate;
 }

 public Long getGenreId() {
  return genreId;
 }

 public void setGenreId(Long genreId) {
  this.genreId = genreId;
 }

 public Long getCountryId() {
  return countryId;
 }

 public void setCountryId(Long countryId) {
  this.countryId = countryId;
 }

 public Long getAgeLimitId() {
  return ageLimitId;
 }

 public void setAgeLimitId(Long ageLimitId) {
  this.ageLimitId = ageLimitId;
 }

 public Long getShootingQualityId() {
  return shootingQualityId;
 }

 public void setShootingQualityId(Long shootingQualityId) {
  this.shootingQualityId = shootingQualityId;
 }

 public Set<Long> getActorIds() {
  return actorIds;
 }

 public void setActorIds(Set<Long> actorIds) {
  this.actorIds = actorIds;
 }
}