package com.hellgardd.filmarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "films")
public class Film {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(
         nullable = false,
         unique = true,
         length = 200
 )
 private String title;

 @Column(length = 5000)
 private String description;

 @Column(nullable = false)
 private Integer duration;

 @Column(
         nullable = false,
         precision = 15,
         scale = 2
 )
 private BigDecimal budget;

 @Column(
         name = "total_box_office",
         nullable = false,
         precision = 15,
         scale = 2
 )
 private BigDecimal totalBoxOffice;

 @Column(
         name = "release_date",
         nullable = false
 )
 private LocalDate releaseDate;

 @ManyToOne(
         fetch = FetchType.LAZY,
         optional = false
 )
 @JoinColumn(
         name = "genre_id",
         nullable = false
 )
 private Genre genre;

 @ManyToOne(
         fetch = FetchType.LAZY,
         optional = false
 )
 @JoinColumn(
         name = "country_id",
         nullable = false
 )
 private Country country;

 @ManyToOne(
         fetch = FetchType.LAZY,
         optional = false
 )
 @JoinColumn(
         name = "age_limit_id",
         nullable = false
 )
 private AgeLimit ageLimit;

 @ManyToOne(
         fetch = FetchType.LAZY,
         optional = false
 )
 @JoinColumn(
         name = "shooting_quality_id",
         nullable = false
 )
 private ShootingQuality shootingQuality;

 @ManyToMany
 @JoinTable(
         name = "film_people",
         joinColumns = @JoinColumn(name = "film_id"),
         inverseJoinColumns = @JoinColumn(name = "person_id"),
         uniqueConstraints = @UniqueConstraint(
                 columnNames = {
                         "film_id",
                         "person_id"
                 }
         )
 )
 @OrderBy("fullName ASC")
 private Set<Person> actors = new LinkedHashSet<>();

 public Film() {
 }

 public Long getId() {
  return id;
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

 public Genre getGenre() {
  return genre;
 }

 public void setGenre(Genre genre) {
  this.genre = genre;
 }

 public Country getCountry() {
  return country;
 }

 public void setCountry(Country country) {
  this.country = country;
 }

 public AgeLimit getAgeLimit() {
  return ageLimit;
 }

 public void setAgeLimit(AgeLimit ageLimit) {
  this.ageLimit = ageLimit;
 }

 public ShootingQuality getShootingQuality() {
  return shootingQuality;
 }

 public void setShootingQuality(ShootingQuality shootingQuality) {
  this.shootingQuality = shootingQuality;
 }

 public Set<Person> getActors() {
  return actors;
 }

 public void setActors(Set<Person> actors) {
  this.actors = actors;
 }

 public String getActorNames() {
  return actors.stream()
          .map(Person::getFullName)
          .sorted()
          .collect(Collectors.joining(", "));
 }
}