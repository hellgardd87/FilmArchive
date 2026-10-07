package com.hellgardd.filmarchive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AgeLimitForm {

 @NotBlank(message = "Укажите категорию")
 @Size(
         max = 120,
         message = "Категория: не более 120 символов"
 )
 private String category;

 @NotBlank(message = "Укажите код")
 @Size(
         max = 120,
         message = "Код: не более 120 символов"
 )
 private String code;

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