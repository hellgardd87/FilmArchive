package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.WeatherResult;
import com.hellgardd.filmarchive.exception.ExternalServiceException;
import com.hellgardd.filmarchive.exception.FormValidationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {
 public record Location(String name, String country, Double latitude, Double longitude) {}
 public record Locations(List<Location> results) {}
 public record Current(Double temperature_2m, Double apparent_temperature, Integer weather_code, String time) {}
 public record Forecast(Current current) {}
 private final RestClient geocoding;
 private final RestClient weather;
 public WeatherService(@Value("${weather.api.url}") String url, @Value("${weather.geocoding.url}") String geocodingUrl) {
  var factory = new SimpleClientHttpRequestFactory();
  factory.setConnectTimeout(3000);
  factory.setReadTimeout(8000);
  weather = RestClient.builder().baseUrl(url).requestFactory(factory).build();
  geocoding = RestClient.builder().baseUrl(geocodingUrl).requestFactory(factory).build();
 }
 public WeatherResult current(String city) {
  if (city == null || city.strip().length() < 2 || city.length() > 100)
   throw new IllegalArgumentException("Введите название города длиной от 2 до 100 символов");
  try {
   Locations locations = geocoding.get().uri(b -> b.path("/v1/search").queryParam("name", "{city}").queryParam("count", 1).queryParam("language", "ru").build(city.strip())).retrieve().body(Locations.class);
   if (locations == null) throw new IllegalStateException("Invalid geocoding response");
   if (locations.results() == null || locations.results().isEmpty())
    throw new FormValidationException("city", "Город не найден. Уточните название города");
   Location location = locations.results().getFirst();
   if (location.latitude() == null || location.longitude() == null) throw new IllegalStateException("Missing coordinates");
   Forecast forecast = weather.get().uri(b -> b.path("/v1/forecast").queryParam("latitude", location.latitude()).queryParam("longitude", location.longitude()).queryParam("current", "temperature_2m,apparent_temperature,weather_code").queryParam("timezone", "auto").build()).retrieve().body(Forecast.class);
   if (forecast == null || forecast.current() == null) throw new IllegalStateException("Missing weather");
   Current current = forecast.current();
   if (current.temperature_2m() == null || current.apparent_temperature() == null || current.weather_code() == null || current.time() == null || !Double.isFinite(current.temperature_2m()) || !Double.isFinite(current.apparent_temperature())) throw new IllegalStateException("Invalid weather");
   return new WeatherResult(location.name(), location.country(), current.temperature_2m(), current.apparent_temperature(), description(current.weather_code()), current.time());
  } catch (FormValidationException ex) { throw ex; }
  catch (RuntimeException ex) { throw new ExternalServiceException(ex); }
 }
 private String description(int code) {
  return switch (code) {
   case 0 -> "Ясно";
   case 1, 2 -> "Переменная облачность";
   case 3 -> "Пасмурно";
   case 45, 48 -> "Туман";
   case 51, 53, 55, 56, 57 -> "Морось";
   case 61, 63, 65, 66, 67, 80, 81, 82 -> "Дождь";
   case 71, 73, 75, 77, 85, 86 -> "Снег";
   case 95, 96, 99 -> "Гроза";
   default -> "Описание погоды отсутствует";
  };
 }
}
