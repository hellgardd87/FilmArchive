package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.AgeLimitForm;
import com.hellgardd.filmarchive.dto.QualityForm;
import com.hellgardd.filmarchive.entity.AgeLimit;
import com.hellgardd.filmarchive.entity.ShootingQuality;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.repository.AgeLimitRepository;
import com.hellgardd.filmarchive.repository.ShootingQualityRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReferenceService {

 private final AgeLimitRepository ages;
 private final ShootingQualityRepository qualities;
 private final TextNormalizer text;

 public ReferenceService(
         AgeLimitRepository ages,
         ShootingQualityRepository qualities,
         TextNormalizer text
 ) {
  this.ages = ages;
  this.qualities = qualities;
  this.text = text;
 }

 public List<AgeLimit> ages() {
  return ages.findAllByOrderByCategoryAsc();
 }

 public List<ShootingQuality> qualities() {
  return qualities.findAllByOrderByTypeAsc();
 }

 @Transactional
 public void addAge(AgeLimitForm form) {
  String category = text.normalize(form.getCategory());
  String code = text.normalize(form.getCode());

  if (category.isBlank()) {
   throw new FormValidationException(
           "category",
           "Заполните категорию"
   );
  }

  if (code.isBlank()) {
   throw new FormValidationException(
           "code",
           "Заполните код"
   );
  }

  if (ages.existsByCategoryIgnoreCase(category)) {
   throw new FormValidationException(
           "category",
           "Категория уже существует"
   );
  }

  if (ages.existsByCodeIgnoreCase(code)) {
   throw new FormValidationException(
           "code",
           "Код уже существует"
   );
  }

  ages.saveAndFlush(
          new AgeLimit(category, code)
  );
 }

 @Transactional
 public void addQuality(QualityForm form) {
  String type = text.normalize(form.getType());

  if (type.isBlank()) {
   throw new FormValidationException(
           "type",
           "Укажите качество"
   );
  }

  if (qualities.existsByTypeIgnoreCase(type)) {
   throw new FormValidationException(
           "type",
           "Такое качество уже существует"
   );
  }

  qualities.saveAndFlush(
          new ShootingQuality(type)
  );
 }
}