package com.hellgardd.filmarchive;
import com.hellgardd.filmarchive.dto.*;
import com.hellgardd.filmarchive.entity.*;
import com.hellgardd.filmarchive.exception.*;
import com.hellgardd.filmarchive.repository.*;
import com.hellgardd.filmarchive.service.*;
import jakarta.validation.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class FilmServiceTest {
 private FilmRepository films; private FilmService service; private ValidatorFactory factory;
 @BeforeEach void setup() {
 films=mock(FilmRepository.class); factory=Validation.buildDefaultValidatorFactory();
 service=new FilmService(films,mock(GenreRepository.class),mock(CountryRepository.class),mock(AgeLimitRepository.class),
 mock(ShootingQualityRepository.class),mock(PersonRepository.class),Clock.fixed(Instant.parse("2026-10-07T10:00:00Z"),ZoneOffset.UTC),factory.getValidator(),new TextNormalizer());
 }
 @AfterEach void close() { factory.close(); }
 @Test void futureReleaseIsRejectedBeforeWriting() { var f=new FilmFormTest().valid(); f.setReleaseDate(LocalDate.of(2026,10,8)); FormValidationException ex=assertThrows(FormValidationException.class,()->service.add(f)); assertTrue(ex.getErrors().containsKey("releaseDate")); verify(films,never()).saveAndFlush(any()); }
 @Test void duplicateTitleUsesNormalizedText() { var f=new FilmFormTest().valid(); f.setTitle("  Тест  "); when(films.existsByTitleIgnoreCase("Тест")).thenReturn(true); assertThrows(FormValidationException.class,()->service.add(f)); verify(films,never()).saveAndFlush(any()); }
 @Test void missingFilmIsNotFound() { when(films.findById(99L)).thenReturn(Optional.empty()); assertThrows(NotFoundException.class,()->service.delete(99L)); }
 @Test void deletionFlushesLinksBeforeDeletingFilm() {
 Film f=new Film(); f.getActors().add(new Person("Актёр")); when(films.findById(1L)).thenReturn(Optional.of(f));
 doAnswer(call -> { assertTrue(f.getActors().isEmpty()); return null; }).when(films).flush();
 service.delete(1L); var order=inOrder(films); order.verify(films).findById(1L); order.verify(films).flush(); order.verify(films).delete(f); order.verify(films).flush();
 }
 @Test void personDeletionRemovesLinksBeforeDeletingPerson() {
 PersonRepository people=mock(PersonRepository.class); Person actor=mock(Person.class); when(actor.getId()).thenReturn(7L);
 Film f=new Film(); f.getActors().add(actor); when(people.findById(7L)).thenReturn(Optional.of(actor)); when(films.findByActorsId(7L)).thenReturn(List.of(f));
 new PersonService(people,films,new TextNormalizer()).delete(7L);
 assertTrue(f.getActors().isEmpty()); var order=inOrder(films,people); order.verify(people).findById(7L); order.verify(films).findByActorsId(7L); order.verify(films).flush(); order.verify(people).delete(actor); verify(films,never()).delete(any());
 }
 @Test void normalizerCollapsesWhitespaceAndUnicodeVariants() { assertEquals("HD",new TextNormalizer().normalize("  ＨＤ  ")); assertEquals("Киану Ривз",new TextNormalizer().normalize(" Киану   Ривз ")); }
}
