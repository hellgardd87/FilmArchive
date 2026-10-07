package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.PersonForm;
import com.hellgardd.filmarchive.entity.Person;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.exception.NotFoundException;
import com.hellgardd.filmarchive.repository.FilmRepository;
import com.hellgardd.filmarchive.repository.PersonRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonService {

 private final PersonRepository people;
 private final FilmRepository films;
 private final TextNormalizer text;

 public PersonService(
         PersonRepository people,
         FilmRepository films,
         TextNormalizer text
 ) {
  this.people = people;
  this.films = films;
  this.text = text;
 }

 public List<Person> all() {
  return people.findAllByOrderByFullNameAsc();
 }

 @Transactional
 public void add(PersonForm form) {
  String name = text.normalize(form.getFullName());

  if (name.isBlank()) {
   throw new FormValidationException(
           "fullName",
           "Укажите ФИО"
   );
  }

  if (people.existsByFullNameIgnoreCase(name)) {
   throw new FormValidationException(
           "fullName",
           "Актёр с таким ФИО уже существует"
   );
  }

  people.saveAndFlush(
          new Person(name)
  );
 }

 @Transactional
 public void delete(Long id) {
  Person person = people.findById(id)
          .orElseThrow(
                  () -> new NotFoundException("Актёр не найден")
          );

  films.findByActorsId(id)
          .forEach(
                  film -> film.getActors()
                          .removeIf(personItem ->
                                  personItem.getId().equals(id)
                          )
          );

  films.flush();

  people.delete(person);
  people.flush();
 }
}