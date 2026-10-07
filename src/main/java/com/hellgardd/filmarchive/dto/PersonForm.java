package com.hellgardd.filmarchive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PersonForm {

 @NotBlank(message = "Заполните название / ФИО")
 @Size(
         max = 120,
         message = "Не более 120 символов"
 )
 private String fullName;

 public String getFullName() {
  return fullName;
 }

 public void setFullName(String fullName) {
  this.fullName = fullName;
 }
}