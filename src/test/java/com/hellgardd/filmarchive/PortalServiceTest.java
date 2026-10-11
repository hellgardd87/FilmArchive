package com.hellgardd.filmarchive;

import com.hellgardd.filmarchive.service.*;
import java.math.BigDecimal;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortalServiceTest {
 @Test void siteSearchNormalizesCaseWhitespaceAndMatchesKeywords() {
  var search = new SiteSearchService();
  assertTrue(search.search(" ИСТОРИЯ ").stream().anyMatch(p -> p.url().equals("/history")));
  assertTrue(search.search("жанры").stream().anyMatch(p -> p.url().equals("/genres-info")));
  assertTrue(search.search("история   кино").stream().anyMatch(p -> p.url().equals("/history")));
  assertTrue(search.search("ЛЮМЬЕР").stream().anyMatch(p -> p.url().equals("/history")));
  assertTrue(search.search("актёры").stream().anyMatch(p -> p.url().equals("/catalog")));
  assertTrue(search.search("абракадабра").isEmpty());
  assertTrue(search.search("  ").isEmpty());
  assertEquals(10, search.services().size());
  assertThrows(IllegalArgumentException.class, () -> search.search("x".repeat(201)));
 }
 @Test void watchTimeConvertsHoursAndRejectsInvalidInput() {
  var service = new WatchTimeService();
  var result = service.calculate(3, 95);
  assertEquals(285, result.minutes()); assertEquals(4, result.hours()); assertEquals(45, result.remainingMinutes());
  assertEquals(14400000, service.calculate(10000, 1440).minutes());
  assertThrows(IllegalArgumentException.class, () -> service.calculate(0, 90));
  assertThrows(IllegalArgumentException.class, () -> service.calculate(2, -1));
 }
 @Test void profitabilityHandlesLossZeroRevenueAndInvalidBudget() {
  var service = new ProfitabilityService();
  var result = service.calculate(new BigDecimal("100.00"), new BigDecimal("250.00"));
  assertEquals(new BigDecimal("2.5000"), result.ratio());
  assertEquals(new BigDecimal("150.00"), result.profit());
  assertEquals(new BigDecimal("-100"), service.calculate(new BigDecimal("100"), BigDecimal.ZERO).profit());
  assertThrows(IllegalArgumentException.class, () -> service.calculate(BigDecimal.ZERO, BigDecimal.TEN));
  assertThrows(IllegalArgumentException.class, () -> service.calculate(new BigDecimal("0.001"), BigDecimal.TEN));
 }
 @Test void ageCheckChecksBoundaryAndRejectsUnknownRating() {
  var service = new AgeCheckService();
  assertTrue(service.check(18, 18)); assertFalse(service.check(17, 18)); assertTrue(service.check(0, 0));
  assertThrows(IllegalArgumentException.class, () -> service.check(20, 10));
  assertThrows(IllegalArgumentException.class, () -> service.check(-1, 0));
 }
 @Test void randomFilmHandlesEmptyCatalogAndUsesExistingFilmService() {
  var films = mock(FilmService.class);
  when(films.all()).thenReturn(java.util.List.of());
  assertTrue(new RandomFilmService(films).random().isEmpty());
  var film = new com.hellgardd.filmarchive.entity.Film();
  when(films.all()).thenReturn(java.util.List.of(film));
  assertSame(film, new RandomFilmService(films).random().orElseThrow());
 }
 @Test void sitemapIsValidXmlUsesConfiguredOriginAndExcludesSearchAndAdmin() throws Exception {
  var seo = new SeoService("https://filmarchive.example/", new SiteSearchService());
  var xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(seo.sitemap().getBytes(StandardCharsets.UTF_8)));
  assertEquals("urlset", xml.getDocumentElement().getNodeName());
  assertTrue(seo.sitemap().contains("https://filmarchive.example/catalog"));
  assertFalse(seo.sitemap().contains("/edit")); assertFalse(seo.sitemap().contains("/site-search"));
  assertTrue(seo.robots().contains("Sitemap: https://filmarchive.example/sitemap.xml"));
  assertThrows(IllegalArgumentException.class, () -> new SeoService("https://filmarchive.example/path", new SiteSearchService()));
 }
}
