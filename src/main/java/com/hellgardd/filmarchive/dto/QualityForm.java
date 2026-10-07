package com.hellgardd.filmarchive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QualityForm {

 @NotBlank(message = "Укажите качество съёмки")
 @Size(
         max = 120,
         message = "Не более 120 символов"
 )
 private String type;

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }
}