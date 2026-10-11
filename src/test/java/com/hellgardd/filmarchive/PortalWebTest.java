package com.hellgardd.filmarchive;

import com.hellgardd.filmarchive.controller.*;
import com.hellgardd.filmarchive.service.*;
import com.hellgardd.filmarchive.entity.*;
import com.hellgardd.filmarchive.exception.ExternalServiceException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest({CatalogController.class, StaticPageController.class, ServiceController.class, SiteSearchController.class, SeoController.class})
@Import({SiteSearchService.class, SeoService.class, RandomFilmService.class, WatchTimeService.class, ProfitabilityService.class, AgeCheckService.class, PortalWebTest.TimeConfig.class})
class PortalWebTest {
 @TestConfiguration static class TimeConfig { @Bean Clock clock() { return Clock.systemUTC(); } }
 @Autowired MockMvc mvc;
 @MockitoBean FilmService films;
 @MockitoBean GenreService genres;
 @MockitoBean CountryService countries;
 @MockitoBean PersonService people;
 @MockitoBean ReferenceService references;
 @MockitoBean SearchService search;
 @MockitoBean StatisticsService statistics;
 @MockitoBean CurrencyService currency;
 @MockitoBean WeatherService weather;
 private void saveHtml(String route, String html) throws java.io.IOException {
  var directory = java.nio.file.Path.of("target", "rendered-html");
  java.nio.file.Files.createDirectories(directory);
  java.nio.file.Files.writeString(directory.resolve(route.equals("/") ? "index.html" : route.substring(1).replace('/', '-') + ".html"), html, java.nio.charset.StandardCharsets.UTF_8);
 }
 @Test void everyPublicPageRendersCommonNavigationSearchAndFooter() throws Exception {
  when(currency.currencies()).thenReturn(List.of("USD", "EUR"));
  when(statistics.get()).thenReturn(new com.hellgardd.filmarchive.dto.Statistics(0,0,0,0,0,0,0));
  var routes = new ArrayList<>(new SiteSearchService().pages().stream().map(p -> p.url()).toList());
  routes.add("/edit");
  for (String route : routes) {
   String html = mvc.perform(get(route)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
   assertTrue(html.contains("Поиск по сайту"), route);
   assertTrue(html.contains("Даниил Сабадыш"), route);
   assertTrue(html.contains("sabadyshdv23@oiate.ru"), route);
   assertTrue(html.contains("https://t.me/Hellgard"), route);
   assertTrue(html.contains("<main"), route);
   assertFalse(html.contains("th:replace"), route);
   saveHtml(route, html);
  }
 }
 @Test void catalogRendersAllFilmFieldsWithoutAdministrativeForms() throws Exception {
  var film = new Film(); film.setTitle("Проверочный фильм"); film.setDescription("Описание");
  film.setDuration(100); film.setReleaseDate(LocalDate.of(2020,1,1));
  film.setBudget(new BigDecimal("1000000")); film.setTotalBoxOffice(new BigDecimal("2500000"));
  var genre = new Genre(); genre.setTitle("Драма"); film.setGenre(genre);
  var country = new Country(); country.setTitle("Россия"); film.setCountry(country);
  var age = new AgeLimit(); age.setCategory("12+"); film.setAgeLimit(age);
  var quality = new ShootingQuality(); quality.setType("Full HD"); film.setShootingQuality(quality);
  var actor = new Person(); actor.setFullName("Тестовый актёр"); film.setActors(new LinkedHashSet<>(List.of(actor)));
  when(films.all()).thenReturn(List.of(film));
  String html = mvc.perform(get("/catalog")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
  for (String text : List.of("Проверочный фильм", "Драма", "Россия", "100 мин", "01.01.2020", "12+", "Full HD", "Тестовый актёр", "Бюджет", "Кассовые сборы")) assertTrue(html.contains(text), text);
  saveHtml("/catalog", html);
  assertFalse(html.contains("/delete_film")); assertFalse(html.contains("/add_film"));
  mvc.perform(get("/services/random-film")).andExpect(status().isOk());
  when(films.form(1L)).thenReturn(new com.hellgardd.filmarchive.dto.FilmForm());
  saveHtml("/edit-film", mvc.perform(get("/edit_film/1")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8));
 }
 @Test void searchSuggestionsReturnPartialMatchesAndPreserveHtmlSearch() throws Exception {
  mvc.perform(get("/site-search").param("suggest", "true").param("query", " ИСТ "))
   .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("application/json"))
   .andExpect(jsonPath("$[?(@.url == '/history')].title").value(org.hamcrest.Matchers.hasItem("История кинематографа")));
  mvc.perform(get("/site-search").param("suggest", "true").param("query", "несуществующаястрока"))
   .andExpect(status().isOk()).andExpect(content().json("[]"));
  mvc.perform(get("/site-search").param("suggest", "true").param("query", "   "))
   .andExpect(status().isOk()).andExpect(content().json("[]"));
  mvc.perform(get("/site-search").param("suggest", "true").param("query", "а"))
   .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(8));
  mvc.perform(get("/site-search").param("suggest", "true").param("query", "x".repeat(201)))
   .andExpect(status().isBadRequest());
  mvc.perform(get("/site-search").param("query", "история"))
   .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/html"))
   .andExpect(content().string(org.hamcrest.Matchers.containsString("История кинематографа")));
 }
 @Test void searchCalculationsAndExternalFailuresRenderSafeResults() throws Exception {
  mvc.perform(get("/site-search").param("query", "история")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("/history")));
  mvc.perform(get("/site-search").param("query", "несуществующаястрока")).andExpect(content().string(org.hamcrest.Matchers.containsString("По вашему запросу ничего не найдено.")));
  mvc.perform(get("/services/watch-time").param("count", "3").param("duration", "95")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("285 мин")));
  mvc.perform(get("/services/profitability").param("budget", "100").param("boxOffice", "250")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("2.5000")));
  mvc.perform(get("/services/age-check").param("age", "17").param("rating", "18")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("не соответствует")));
  mvc.perform(get("/services/watch-time").param("count", "-1").param("duration", "90")).andExpect(status().isBadRequest());
  when(currency.currencies()).thenReturn(List.of("USD", "EUR"));
  when(currency.convert(BigDecimal.TEN, "USD", "EUR")).thenReturn(new com.hellgardd.filmarchive.dto.CurrencyResult(BigDecimal.TEN, "USD", "EUR", new BigDecimal("0.9"), new BigDecimal("9.00"), LocalDate.of(2026,10,7)));
  saveHtml("/services/currency", mvc.perform(get("/services/currency").param("amount", "10")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("9.00 EUR"))).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8));
  when(weather.current("Москва")).thenReturn(new com.hellgardd.filmarchive.dto.WeatherResult("Москва", "Россия", 10.5, 8.2, "Пасмурно", "2026-10-08T12:00"));
  saveHtml("/services/weather", mvc.perform(get("/services/weather").param("city", "Москва")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("Пасмурно"))).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8));
  when(weather.current("Москва")).thenThrow(new ExternalServiceException(new RuntimeException("private trace")));
  String html = mvc.perform(get("/services/weather").param("city", "Москва")).andExpect(status().isServiceUnavailable()).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
  saveHtml("/error", html);
  assertTrue(html.contains("Сервис временно недоступен")); assertFalse(html.contains("private trace"));
  mvc.perform(get("/missing-page")).andExpect(status().isNotFound());
  mvc.perform(get("/robots.txt")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("Sitemap:")));
  mvc.perform(get("/sitemap.xml")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("application/xml"));
 }
}
