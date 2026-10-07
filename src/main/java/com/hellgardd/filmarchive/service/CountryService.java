package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.ReferenceForm;
import com.hellgardd.filmarchive.entity.Country;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.repository.CountryRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CountryService {

 private final CountryRepository repository;
 private final TextNormalizer text;

 public CountryService(
         CountryRepository repository,
         TextNormalizer text
 ) {
  this.repository = repository;
  this.text = text;
 }

 public List<Country> all() {
  return repository.findAllByOrderByTitleAsc();
 }

 @Transactional
 public void add(ReferenceForm form) {
  String title = text.normalize(form.getTitle());
  String code = text.normalize(form.getCode());

  if (title.isBlank()) {
   throw new FormValidationException(
           "title",
           "Заполните название"
   );
  }

  if (code.isBlank()) {
   throw new FormValidationException(
           "code",
           "Заполните код"
   );
  }

  if (repository.existsByTitleIgnoreCase(title)) {
   throw new FormValidationException(
           "title",
           "Такое название уже существует"
   );
  }

  if (repository.existsByCodeIgnoreCase(code)) {
   throw new FormValidationException(
           "code",
           "Такой код уже существует"
   );
  }

  repository.saveAndFlush(
          new Country(title, code)
  );
 }
}