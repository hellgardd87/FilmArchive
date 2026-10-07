package com.hellgardd.filmarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "age_limits")
public class AgeLimit {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(
         nullable = false,
         unique = true,
         length = 120
 )
 private String category;

 @Column(
         nullable = false,
         unique = true,
         length = 120
 )
 private String code;

 public AgeLimit() {
 }

 public AgeLimit(String category, String code) {
  this.category = category;
  this.code = code;
 }

 public Long getId() {
  return id;
 }

 public String getCategory() {
  return category;
 }

 public void setCategory(String category) {
  this.category = category;
 }

 public String getCode() {
  return code;
 }

 public void setCode(String code) {
  this.code = code;
 }
}