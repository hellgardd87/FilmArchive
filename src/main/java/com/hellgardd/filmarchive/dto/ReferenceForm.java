package com.hellgardd.filmarchive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReferenceForm {

 @NotBlank(message = "Укажите название")
 @Size(
         max = 120,
         message = "Название: не более 120 символов"
 )
 private String title;

 @NotBlank(message = "Укажите код")
 @Size(
         max = 120,
         message = "Код: не более 120 символов"
 )
 private String code;

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