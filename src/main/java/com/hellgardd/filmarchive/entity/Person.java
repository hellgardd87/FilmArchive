package com.hellgardd.filmarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class Person {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(
         name = "full_name",
         nullable = false,
         unique = true,
         length = 120
 )
 private String fullName;

 public Person() {
 }

 public Person(String fullName) {
  this.fullName = fullName;
 }

 public Long getId() {
  return id;
 }

 public String getFullName() {
  return fullName;
 }

 public void setFullName(String fullName) {
  this.fullName = fullName;
 }
}