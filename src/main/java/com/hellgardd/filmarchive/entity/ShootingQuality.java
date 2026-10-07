package com.hellgardd.filmarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shooting_qualities")
public class ShootingQuality {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(
         nullable = false,
         unique = true,
         length = 120
 )
 private String type;

 public ShootingQuality() {
 }

 public ShootingQuality(String type) {
  this.type = type;
 }

 public Long getId() {
  return id;
 }

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }
}