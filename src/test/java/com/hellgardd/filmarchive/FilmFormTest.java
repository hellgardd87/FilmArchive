package com.hellgardd.filmarchive;
import com.hellgardd.filmarchive.dto.FilmForm;
import jakarta.validation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
class FilmFormTest {
 private ValidatorFactory factory; private Validator validator;
 @BeforeEach void setup() { factory=Validation.buildDefaultValidatorFactory(); validator=factory.getValidator(); }
 @AfterEach void close() { factory.close(); }
 FilmForm valid() {
 FilmForm f=new FilmForm(); f.setTitle("Тест"); f.setDuration(120); f.setDescription("Описание");
 f.setBudget(new BigDecimal("1000000")); f.setTotalBoxOffice(new BigDecimal("2000000"));
 f.setReleaseDate(LocalDate.of(2020,1,1)); f.setGenreId(1L); f.setCountryId(1L); f.setAgeLimitId(1L); f.setShootingQualityId(1L); f.setActorIds(Set.of(1L));
 return f;
 }
 @Test void validFormAndUpperDurationBoundary() { FilmForm f=valid(); f.setDuration(300); assertTrue(validator.validate(f).isEmpty()); }
 @Test void rejectsBlankTitleAndMissingActors() { FilmForm f=valid(); f.setTitle("   "); f.setActorIds(Set.of()); var errors=validator.validate(f); assertTrue(errors.stream().anyMatch(e -> e.getPropertyPath().toString().equals("title"))); assertTrue(errors.stream().anyMatch(e -> e.getPropertyPath().toString().equals("actorIds"))); }
 @Test void rejectsDurationOutsideRange() { for(int v:new int[]{0,-1,301}) { FilmForm f=valid(); f.setDuration(v); assertFalse(validator.validate(f).isEmpty()); } }
 @Test void rejectsExclusiveBudgetBounds() { for(String v:new String[]{"0","-1","600000000"}) { FilmForm f=valid(); f.setBudget(new BigDecimal(v)); assertFalse(validator.validate(f).isEmpty()); } }
 @Test void rejectsExclusiveBoxOfficeBounds() { for(String v:new String[]{"0","-1","3000000000"}) { FilmForm f=valid(); f.setTotalBoxOffice(new BigDecimal(v)); assertFalse(validator.validate(f).isEmpty()); } }
 @Test void acceptsMoneyBelowMaximum() { FilmForm f=valid(); f.setBudget(new BigDecimal("599999999.99")); f.setTotalBoxOffice(new BigDecimal("2999999999.99")); assertTrue(validator.validate(f).isEmpty()); }
 @Test void rejectsExcessivePrecisionAndInvalidIdentifiers() { FilmForm f=valid(); f.setBudget(new BigDecimal("1.001")); f.setGenreId(-1L); f.setActorIds(Set.of(-1L)); assertTrue(validator.validate(f).size()>=3); }
}
