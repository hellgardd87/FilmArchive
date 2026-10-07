package com.hellgardd.filmarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(
         nullable = false,
         unique = true,
         length = 120
 )
 private String title;

 @Column(
         nullable = false,
         unique = true,
         length = 120
 )
 private String code;

 public Country() {
 }

 public Country(String title, String code) {
  this.title = title;
  this.code = code;
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

 public String getCode() {
  return code;
 }

 public void setCode(String code) {
  this.code = code;
 }
}