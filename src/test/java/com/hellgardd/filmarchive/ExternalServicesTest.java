package com.hellgardd.filmarchive;

import com.hellgardd.filmarchive.service.*;
import com.hellgardd.filmarchive.exception.*;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ExternalServicesTest {
 private HttpServer server;
 private String origin;
 private int status = 200;
 private String rates = "{\"base\":\"USD\",\"quote\":\"EUR\",\"rate\":0.9,\"date\":\"2026-10-07\"}";
 private String locations = "{\"results\":[{\"name\":\"Москва\",\"country\":\"Россия\",\"latitude\":55.75,\"longitude\":37.62}]}";
 private String forecast = "{\"current\":{\"temperature_2m\":10.5,\"apparent_temperature\":8.2,\"weather_code\":3,\"time\":\"2026-10-08T12:00\"}}";
 @BeforeEach void start() throws Exception {
  server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
  server.createContext("/", exchange -> {
   String path = exchange.getRequestURI().getPath();
   String body = path.startsWith("/v2/") ? rates : path.equals("/v1/search") ? locations : forecast;
   byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
   exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
   exchange.sendResponseHeaders(status, bytes.length);
   try (var out = exchange.getResponseBody()) { out.write(bytes); }
  });
  server.start(); origin = "http://127.0.0.1:" + server.getAddress().getPort();
 }
 @AfterEach void stop() { server.stop(0); }
 @Test void currencyConvertsUsingProviderRateAndDate() {
  var service = new CurrencyService(origin);
  var result = service.convert(new BigDecimal("100.00"), "USD", "EUR");
  assertEquals(new BigDecimal("90.00"), result.converted());
  assertEquals(LocalDate.of(2026, 10, 7), result.date());
  status = 503;
  assertEquals(BigDecimal.TEN, service.convert(BigDecimal.TEN, "USD", "USD").converted());
  assertThrows(ExternalServiceException.class, () -> service.convert(BigDecimal.TEN, "USD", "EUR"));
 }
 @Test void currencyRejectsInvalidInputsAndMalformedApiResponse() {
  var service = new CurrencyService(origin);
  assertThrows(IllegalArgumentException.class, () -> service.convert(BigDecimal.TEN, "ABC", "EUR"));
  rates = "{\"rate\":0}";
  assertThrows(ExternalServiceException.class, () -> service.convert(BigDecimal.TEN, "USD", "EUR"));
 }
 @Test void weatherGeocodesAndShowsTemperatureFeelsLikeAndDescription() {
  var result = new WeatherService(origin, origin).current("Москва");
  assertEquals("Москва", result.city()); assertEquals(10.5, result.temperature());
  assertEquals(8.2, result.feelsLike()); assertEquals("Пасмурно", result.description());
 }
 @Test void weatherHandlesUnknownCityAndBrokenProvider() {
  var service = new WeatherService(origin, origin);
  locations = "{}";
  assertThrows(FormValidationException.class, () -> service.current("Неизвестный"));
  status = 503;
  assertThrows(ExternalServiceException.class, () -> service.current("Москва"));
  assertThrows(IllegalArgumentException.class, () -> service.current(" "));
 }
}
