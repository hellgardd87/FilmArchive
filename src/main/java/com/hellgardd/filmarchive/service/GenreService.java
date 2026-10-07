package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.ReferenceForm;
import com.hellgardd.filmarchive.entity.Genre;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.repository.GenreRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GenreService {

 private final GenreRepository repository;
 private final TextNormalizer text;

 public GenreService(
         GenreRepository repository,
         TextNormalizer text
 ) {
  this.repository = repository;
  this.text = text;
 }

 public List<Genre> all() {
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
          new Genre(title, code)
  );
 }
}