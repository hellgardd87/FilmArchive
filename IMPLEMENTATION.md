# FilmArchive: структура, изменения и код

Изменения уже внесены в существующий проект. Пакет: com.hellgardd.filmarchive. Сущности, repository и работающая логика CRUD сохранены.

## Обновлённая структура

```text
FilmArchive/
├── pom.xml
├── compose.yaml
├── database/create-database.sql
├── README.md
├── IMPLEMENTATION.md          # структура, изменения и код по порядку задания
├── LAB_CHECKLIST.md           # соответствие требованиям
├── VERIFICATION.md            # результаты и ограничения проверки
├── tools/
│   ├── check_contracts.py
│   └── check_rendered_html.py
└── src/
    ├── main/
    │   ├── java/com/hellgardd/filmarchive/
    │   │   ├── FilmArchiveApplication.java
    │   │   ├── config/         # AppConfig, DataInitializer, SiteModelAdvice
    │   │   ├── controller/     # Catalog, StaticPage, Service, SiteSearch, SEO, ошибки
    │   │   ├── dto/            # существующие формы и новые record-результаты
    │   │   ├── entity/         # Film, Genre, Country, AgeLimit, ShootingQuality, Person
    │   │   ├── exception/      # ошибки формы, отсутствующих записей и внешних API
    │   │   ├── repository/     # существующие JPA repository
    │   │   └── service/        # существующие сервисы + поиск сайта, инструменты, API, SEO
    │   └── resources/
    │       ├── application.properties
    │       ├── messages.properties
    │       ├── static/
    │       │   ├── css/style.css
    │       │   ├── js/main.js
    │       │   └── robots.txt
    │       └── templates/
    │           ├── fragments/ # header, footer, messages, film-fields, film-card
    │           ├── index.html, about.html, history.html, genres-info.html
    │           ├── cinema-technologies.html, ratings-info.html, formats.html
    │           ├── recommendations.html, links.html, contacts.html
    │           ├── catalog.html, site-search.html, services.html
    │           ├── edit.html, edit-film.html, search.html, aggregate.html
    │           ├── info.html, error.html
    │           └── services/
    │               ├── random-film.html, watch-time.html, profitability.html
    │               └── age-check.html, currency.html, weather.html
    └── test/java/com/hellgardd/filmarchive/
        ├── FilmFormTest.java, FilmServiceTest.java, CatalogIT.java
        └── PortalServiceTest.java, ExternalServicesTest.java, PortalWebTest.java
```

## 1. Новые файлы

- [LAB_CHECKLIST.md](LAB_CHECKLIST.md)
- [src/main/java/com/hellgardd/filmarchive/config/SiteModelAdvice.java](src/main/java/com/hellgardd/filmarchive/config/SiteModelAdvice.java)
- [src/main/java/com/hellgardd/filmarchive/controller/SeoController.java](src/main/java/com/hellgardd/filmarchive/controller/SeoController.java)
- [src/main/java/com/hellgardd/filmarchive/controller/ServiceController.java](src/main/java/com/hellgardd/filmarchive/controller/ServiceController.java)
- [src/main/java/com/hellgardd/filmarchive/controller/SiteSearchController.java](src/main/java/com/hellgardd/filmarchive/controller/SiteSearchController.java)
- [src/main/java/com/hellgardd/filmarchive/controller/StaticPageController.java](src/main/java/com/hellgardd/filmarchive/controller/StaticPageController.java)
- [src/main/java/com/hellgardd/filmarchive/dto/CurrencyResult.java](src/main/java/com/hellgardd/filmarchive/dto/CurrencyResult.java)
- [src/main/java/com/hellgardd/filmarchive/dto/ProfitabilityResult.java](src/main/java/com/hellgardd/filmarchive/dto/ProfitabilityResult.java)
- [src/main/java/com/hellgardd/filmarchive/dto/SitePage.java](src/main/java/com/hellgardd/filmarchive/dto/SitePage.java)
- [src/main/java/com/hellgardd/filmarchive/dto/WatchTimeResult.java](src/main/java/com/hellgardd/filmarchive/dto/WatchTimeResult.java)
- [src/main/java/com/hellgardd/filmarchive/dto/WeatherResult.java](src/main/java/com/hellgardd/filmarchive/dto/WeatherResult.java)
- [src/main/java/com/hellgardd/filmarchive/exception/ExternalServiceException.java](src/main/java/com/hellgardd/filmarchive/exception/ExternalServiceException.java)
- [src/main/java/com/hellgardd/filmarchive/service/AgeCheckService.java](src/main/java/com/hellgardd/filmarchive/service/AgeCheckService.java)
- [src/main/java/com/hellgardd/filmarchive/service/CurrencyService.java](src/main/java/com/hellgardd/filmarchive/service/CurrencyService.java)
- [src/main/java/com/hellgardd/filmarchive/service/ProfitabilityService.java](src/main/java/com/hellgardd/filmarchive/service/ProfitabilityService.java)
- [src/main/java/com/hellgardd/filmarchive/service/RandomFilmService.java](src/main/java/com/hellgardd/filmarchive/service/RandomFilmService.java)
- [src/main/java/com/hellgardd/filmarchive/service/SeoService.java](src/main/java/com/hellgardd/filmarchive/service/SeoService.java)
- [src/main/java/com/hellgardd/filmarchive/service/SiteSearchService.java](src/main/java/com/hellgardd/filmarchive/service/SiteSearchService.java)
- [src/main/java/com/hellgardd/filmarchive/service/WatchTimeService.java](src/main/java/com/hellgardd/filmarchive/service/WatchTimeService.java)
- [src/main/java/com/hellgardd/filmarchive/service/WeatherService.java](src/main/java/com/hellgardd/filmarchive/service/WeatherService.java)
- [src/main/resources/static/robots.txt](src/main/resources/static/robots.txt)
- [src/main/resources/templates/about.html](src/main/resources/templates/about.html)
- [src/main/resources/templates/catalog.html](src/main/resources/templates/catalog.html)
- [src/main/resources/templates/cinema-technologies.html](src/main/resources/templates/cinema-technologies.html)
- [src/main/resources/templates/contacts.html](src/main/resources/templates/contacts.html)
- [src/main/resources/templates/formats.html](src/main/resources/templates/formats.html)
- [src/main/resources/templates/fragments/film-card.html](src/main/resources/templates/fragments/film-card.html)
- [src/main/resources/templates/genres-info.html](src/main/resources/templates/genres-info.html)
- [src/main/resources/templates/history.html](src/main/resources/templates/history.html)
- [src/main/resources/templates/links.html](src/main/resources/templates/links.html)
- [src/main/resources/templates/ratings-info.html](src/main/resources/templates/ratings-info.html)
- [src/main/resources/templates/recommendations.html](src/main/resources/templates/recommendations.html)
- [src/main/resources/templates/services.html](src/main/resources/templates/services.html)
- [src/main/resources/templates/services/age-check.html](src/main/resources/templates/services/age-check.html)
- [src/main/resources/templates/services/currency.html](src/main/resources/templates/services/currency.html)
- [src/main/resources/templates/services/profitability.html](src/main/resources/templates/services/profitability.html)
- [src/main/resources/templates/services/random-film.html](src/main/resources/templates/services/random-film.html)
- [src/main/resources/templates/services/watch-time.html](src/main/resources/templates/services/watch-time.html)
- [src/main/resources/templates/services/weather.html](src/main/resources/templates/services/weather.html)
- [src/main/resources/templates/site-search.html](src/main/resources/templates/site-search.html)
- [src/test/java/com/hellgardd/filmarchive/ExternalServicesTest.java](src/test/java/com/hellgardd/filmarchive/ExternalServicesTest.java)
- [src/test/java/com/hellgardd/filmarchive/PortalServiceTest.java](src/test/java/com/hellgardd/filmarchive/PortalServiceTest.java)
- [src/test/java/com/hellgardd/filmarchive/PortalWebTest.java](src/test/java/com/hellgardd/filmarchive/PortalWebTest.java)
- [tools/check_rendered_html.py](tools/check_rendered_html.py)
- [IMPLEMENTATION.md](IMPLEMENTATION.md)

## 2. Изменённые файлы

- [README.md](README.md)
- [VERIFICATION.md](VERIFICATION.md)
- [pom.xml](pom.xml)
- [src/main/java/com/hellgardd/filmarchive/controller/CatalogController.java](src/main/java/com/hellgardd/filmarchive/controller/CatalogController.java)
- [src/main/java/com/hellgardd/filmarchive/controller/GlobalExceptionHandler.java](src/main/java/com/hellgardd/filmarchive/controller/GlobalExceptionHandler.java)
- [src/main/resources/application.properties](src/main/resources/application.properties)
- [src/main/resources/static/css/style.css](src/main/resources/static/css/style.css)
- [src/main/resources/static/js/main.js](src/main/resources/static/js/main.js)
- [src/main/resources/templates/edit.html](src/main/resources/templates/edit.html)
- [src/main/resources/templates/fragments/footer.html](src/main/resources/templates/fragments/footer.html)
- [src/main/resources/templates/fragments/header.html](src/main/resources/templates/fragments/header.html)
- [src/main/resources/templates/index.html](src/main/resources/templates/index.html)
- [tools/check_contracts.py](tools/check_contracts.py)

## 3. Добавленные URL

- `/about` — О проекте.
- `/history` — История кинематографа.
- `/genres-info` — Основные жанры кино.
- `/cinema-technologies` — Технологии в кинематографе.
- `/ratings-info` — Возрастные рейтинги.
- `/formats` — Форматы и качество видео.
- `/recommendations` — Как выбрать фильм.
- `/links` — Полезные ресурсы о кино.
- `/contacts` — Контактная информация.
- `/catalog` — Каталог фильмов.
- `/site-search` — Поиск по сайту.
- `/services` — Сервисы.
- `/services/random-film` — Случайный фильм.
- `/services/watch-time` — Калькулятор времени просмотра.
- `/services/profitability` — Калькулятор окупаемости фильма.
- `/services/age-check` — Проверка возрастного рейтинга.
- `/services/currency` — Конвертер валют.
- `/services/weather` — Погода.
- `/robots.txt` — правила обхода и ссылка на sitemap.
- `/sitemap.xml` — XML публичных страниц.

Существующие `/`, `/edit`, `/edit_film/{id}`, `/search`, `/aggregate`, `/info`, `/get_countries` и POST CRUD-маршруты сохранены.

## Код реализации в порядке задания

### 1. DTO

#### [src/main/java/com/hellgardd/filmarchive/dto/SitePage.java](src/main/java/com/hellgardd/filmarchive/dto/SitePage.java)

```java
package com.hellgardd.filmarchive.dto;

public record SitePage(String title, String description, String url) {}

```

#### [src/main/java/com/hellgardd/filmarchive/dto/WatchTimeResult.java](src/main/java/com/hellgardd/filmarchive/dto/WatchTimeResult.java)

```java
package com.hellgardd.filmarchive.dto;

public record WatchTimeResult(long minutes, long hours, long remainingMinutes) {}

```

#### [src/main/java/com/hellgardd/filmarchive/dto/ProfitabilityResult.java](src/main/java/com/hellgardd/filmarchive/dto/ProfitabilityResult.java)

```java
package com.hellgardd.filmarchive.dto;

public record ProfitabilityResult(java.math.BigDecimal ratio, java.math.BigDecimal profit) {}

```

#### [src/main/java/com/hellgardd/filmarchive/dto/CurrencyResult.java](src/main/java/com/hellgardd/filmarchive/dto/CurrencyResult.java)

```java
package com.hellgardd.filmarchive.dto;

public record CurrencyResult(java.math.BigDecimal amount, String from, String to, java.math.BigDecimal rate, java.math.BigDecimal converted, java.time.LocalDate date) {}

```

#### [src/main/java/com/hellgardd/filmarchive/dto/WeatherResult.java](src/main/java/com/hellgardd/filmarchive/dto/WeatherResult.java)

```java
package com.hellgardd.filmarchive.dto;

public record WeatherResult(String city, String country, double temperature, double feelsLike, String description, String observedAt) {}

```

### 2. Service

#### [src/main/java/com/hellgardd/filmarchive/service/SiteSearchService.java](src/main/java/com/hellgardd/filmarchive/service/SiteSearchService.java)

```java
package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.SitePage;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class SiteSearchService {
 private record Entry(SitePage page, String keywords) {}
 private final List<Entry> entries = List.of(
  new Entry(new SitePage("О системе", "Возможности и порядок работы с каталогом FilmArchive.", "/info"), "система управление"),
  new Entry(new SitePage("Главная", "Информационный портал о кино, фильмах и актёрах.", "/"), "кино кинематограф главная"),
  new Entry(new SitePage("О проекте", "Назначение FilmArchive и возможности портала.", "/about"), "автор разработчик"),
  new Entry(new SitePage("История кинематографа", "От первых кинопоказов до цифрового кино.", "/history"), "история люмьер немое звук"),
  new Entry(new SitePage("Основные жанры кино", "Драма, комедия, фантастика и другие жанры.", "/genres-info"), "жанры жанр боевик"),
  new Entry(new SitePage("Технологии в кинематографе", "Монтаж, звук, компьютерная графика и цифровая съёмка.", "/cinema-technologies"), "технологии cgi эффекты"),
  new Entry(new SitePage("Возрастные рейтинги", "Возрастные обозначения 0+, 6+, 12+, 16+ и 18+.", "/ratings-info"), "рейтинг ограничения дети"),
  new Entry(new SitePage("Форматы и качество видео", "HD, Full HD, 4K, разрешение, контейнеры и кодеки.", "/formats"), "форматы качество видео"),
  new Entry(new SitePage("Как выбрать фильм", "Рекомендации по выбору фильма для просмотра.", "/recommendations"), "рекомендации советы выбор"),
  new Entry(new SitePage("Полезные ресурсы о кино", "Ссылки на базы фильмов, рецензии и архивы.", "/links"), "ресурсы ссылки imdb"),
  new Entry(new SitePage("Контактная информация", "Разработчик Даниил Сабадыш и контакты FilmArchive.", "/contacts"), "контакты email telegram"),
  new Entry(new SitePage("Каталог фильмов", "Фильмы, актёры, бюджет и кассовые сборы из PostgreSQL.", "/catalog"), "каталог коллекция"),
  new Entry(new SitePage("Поиск фильмов", "Фильтрация каталога по жанру и стране.", "/search"), "поиск фильмов страна"),
  new Entry(new SitePage("Поиск по сайту", "Поиск информационных страниц и сервисов портала.", "/site-search"), "глобальный поиск страницы"),
  new Entry(new SitePage("Сервисы", "Десять сервисов для посетителей FilmArchive.", "/services"), "инструменты калькуляторы"),
  new Entry(new SitePage("Статистика каталога", "Количество фильмов, актёров, жанров и связей.", "/aggregate"), "статистика количество"),
  new Entry(new SitePage("Случайный фильм", "Случайный выбор фильма из каталога PostgreSQL.", "/services/random-film"), "случайный подбор"),
  new Entry(new SitePage("Калькулятор времени просмотра", "Общее время просмотра фильмов в минутах и часах.", "/services/watch-time"), "время длительность часы"),
  new Entry(new SitePage("Калькулятор окупаемости фильма", "Отношение сборов к бюджету и разность денежных сумм.", "/services/profitability"), "бюджет прибыль окупаемость сборы"),
  new Entry(new SitePage("Проверка возрастного рейтинга", "Сравнение возраста зрителя с выбранным рейтингом.", "/services/age-check"), "возраст проверка"),
  new Entry(new SitePage("Конвертер валют", "Пересчёт бюджета фильма по курсу Frankfurter.", "/services/currency"), "валюта курс доллар евро рубль"),
  new Entry(new SitePage("Погода", "Погода в городе перед походом в кинотеатр.", "/services/weather"), "погода температура город")
 );
 public List<SitePage> pages() { return entries.stream().map(Entry::page).toList(); }
 public List<SitePage> services() {
  return pages().stream().filter(p -> List.of("/catalog", "/search", "/site-search", "/aggregate").contains(p.url()) || p.url().startsWith("/services/")).toList();
 }
 public List<SitePage> search(String query) {
  if (query == null || query.isBlank()) return List.of();
  if (query.length() > 200) throw new IllegalArgumentException("Запрос не должен превышать 200 символов");
  String[] terms = normalize(query).split("\\s+");
  return entries.stream().filter(e -> {
   String text = normalize(e.page.title() + " " + e.page.description() + " " + e.keywords);
   return java.util.Arrays.stream(terms).allMatch(text::contains);
  }).map(Entry::page).toList();
 }
 private String normalize(String text) {
  return Normalizer.normalize(text, Normalizer.Form.NFKC).toLowerCase(Locale.ROOT).replace('ё', 'е').strip();
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/service/RandomFilmService.java](src/main/java/com/hellgardd/filmarchive/service/RandomFilmService.java)

```java
package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.entity.Film;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class RandomFilmService {
 private final FilmService films;
 public RandomFilmService(FilmService films) { this.films = films; }
 public Optional<Film> random() {
  var all = films.all();
  return all.isEmpty() ? Optional.empty() : Optional.of(all.get(ThreadLocalRandom.current().nextInt(all.size())));
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/service/WatchTimeService.java](src/main/java/com/hellgardd/filmarchive/service/WatchTimeService.java)

```java
package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.WatchTimeResult;
import org.springframework.stereotype.Service;

@Service
public class WatchTimeService {
 public WatchTimeResult calculate(int count, int averageMinutes) {
  if (count < 1 || count > 10000 || averageMinutes < 1 || averageMinutes > 1440)
   throw new IllegalArgumentException("Количество: 1–10000; длительность: 1–1440 минут");
  long total = (long) count * averageMinutes;
  return new WatchTimeResult(total, total / 60, total % 60);
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/service/ProfitabilityService.java](src/main/java/com/hellgardd/filmarchive/service/ProfitabilityService.java)

```java
package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.ProfitabilityResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class ProfitabilityService {
 public ProfitabilityResult calculate(BigDecimal budget, BigDecimal boxOffice) {
  validate(budget, false);
  validate(boxOffice, true);
  return new ProfitabilityResult(boxOffice.divide(budget, 4, RoundingMode.HALF_UP), boxOffice.subtract(budget));
 }
 private void validate(BigDecimal value, boolean allowZero) {
  if (value == null || value.signum() < 0 || (!allowZero && value.signum() == 0) || value.compareTo(new BigDecimal("1000000000000")) > 0 || value.scale() > 2)
   throw new IllegalArgumentException("Бюджет должен быть положительным, сборы — неотрицательными; максимум 1 трлн, два знака после запятой");
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/service/AgeCheckService.java](src/main/java/com/hellgardd/filmarchive/service/AgeCheckService.java)

```java
package com.hellgardd.filmarchive.service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgeCheckService {
 public List<Integer> ratings() { return List.of(0, 6, 12, 16, 18); }
 public boolean check(int age, int rating) {
  if (age < 0 || age > 120 || !ratings().contains(rating))
   throw new IllegalArgumentException("Укажите возраст от 0 до 120 и рейтинг из списка");
  return age >= rating;
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/service/CurrencyService.java](src/main/java/com/hellgardd/filmarchive/service/CurrencyService.java)

```java
package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.CurrencyResult;
import com.hellgardd.filmarchive.exception.ExternalServiceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrencyService {
 public record RateResponse(String base, String quote, BigDecimal rate, LocalDate date) {}
 private final RestClient client;
 public CurrencyService(@Value("${currency.api.url}") String url) {
  var factory = new SimpleClientHttpRequestFactory();
  factory.setConnectTimeout(3000);
  factory.setReadTimeout(8000);
  client = RestClient.builder().baseUrl(url).requestFactory(factory).build();
 }
 public List<String> currencies() { return List.of("USD", "EUR", "RUB", "GBP", "CNY", "JPY"); }
 public CurrencyResult convert(BigDecimal amount, String from, String to) {
  String base = from == null ? "" : from.toUpperCase(Locale.ROOT);
  String quote = to == null ? "" : to.toUpperCase(Locale.ROOT);
  if (amount == null || amount.signum() < 0 || amount.compareTo(new BigDecimal("1000000000000")) > 0 || amount.scale() > 2 || !currencies().contains(base) || !currencies().contains(quote))
   throw new IllegalArgumentException("Укажите сумму от 0 до 1 трлн с двумя знаками после запятой и валюты из списка");
  if (base.equals(quote)) return new CurrencyResult(amount, base, quote, BigDecimal.ONE, amount, LocalDate.now());
  try {
   RateResponse response = client.get().uri("/v2/rate/{base}/{quote}", base, quote).retrieve().body(RateResponse.class);
   if (response == null || response.rate() == null || response.rate().signum() <= 0 || response.date() == null || !base.equalsIgnoreCase(response.base()) || !quote.equalsIgnoreCase(response.quote()))
    throw new IllegalStateException("Invalid currency API response");
   return new CurrencyResult(amount, base, quote, response.rate(), amount.multiply(response.rate()).setScale(2, RoundingMode.HALF_UP), response.date());
  } catch (RuntimeException ex) { throw new ExternalServiceException(ex); }
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/service/WeatherService.java](src/main/java/com/hellgardd/filmarchive/service/WeatherService.java)

```java
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

```

#### [src/main/java/com/hellgardd/filmarchive/exception/ExternalServiceException.java](src/main/java/com/hellgardd/filmarchive/exception/ExternalServiceException.java)

```java
package com.hellgardd.filmarchive.exception;

public class ExternalServiceException extends RuntimeException {
 public ExternalServiceException(Throwable cause) { super("Сервис временно недоступен", cause); }
}

```

### 3. Controller

#### [src/main/java/com/hellgardd/filmarchive/controller/StaticPageController.java](src/main/java/com/hellgardd/filmarchive/controller/StaticPageController.java)

```java
package com.hellgardd.filmarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {
 @GetMapping("/about")
 public String about() { return "about"; }
 @GetMapping("/history")
 public String history() { return "history"; }
 @GetMapping("/genres-info")
 public String genresInfo() { return "genres-info"; }
 @GetMapping("/cinema-technologies")
 public String cinemaTechnologies() { return "cinema-technologies"; }
 @GetMapping("/ratings-info")
 public String ratingsInfo() { return "ratings-info"; }
 @GetMapping("/formats")
 public String formats() { return "formats"; }
 @GetMapping("/recommendations")
 public String recommendations() { return "recommendations"; }
 @GetMapping("/links")
 public String links() { return "links"; }
 @GetMapping("/contacts")
 public String contacts() { return "contacts"; }
}

```

#### [src/main/java/com/hellgardd/filmarchive/controller/ServiceController.java](src/main/java/com/hellgardd/filmarchive/controller/ServiceController.java)

```java
package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.service.*;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ServiceController {
 private final SiteSearchService pages;
 private final RandomFilmService random;
 private final WatchTimeService watchTime;
 private final ProfitabilityService profitability;
 private final AgeCheckService ageCheck;
 private final CurrencyService currency;
 private final WeatherService weather;
 public ServiceController(SiteSearchService pages, RandomFilmService random, WatchTimeService watchTime,
   ProfitabilityService profitability, AgeCheckService ageCheck, CurrencyService currency, WeatherService weather) {
  this.pages = pages; this.random = random; this.watchTime = watchTime; this.profitability = profitability;
  this.ageCheck = ageCheck; this.currency = currency; this.weather = weather;
 }
 @GetMapping("/services")
 public String services(Model model) { model.addAttribute("services", pages.services()); return "services"; }
 @GetMapping("/services/random-film")
 public String random(Model model) { model.addAttribute("film", random.random().orElse(null)); return "services/random-film"; }
 @GetMapping("/services/watch-time")
 public String watchTime(@RequestParam(required = false) Integer count, @RequestParam(required = false) Integer duration, Model model) {
  model.addAttribute("count", count); model.addAttribute("duration", duration);
  if (count != null || duration != null) {
   if (count == null || duration == null) throw new IllegalArgumentException("Заполните оба поля");
   model.addAttribute("result", watchTime.calculate(count, duration));
  }
  return "services/watch-time";
 }
 @GetMapping("/services/profitability")
 public String profitability(@RequestParam(required = false) BigDecimal budget, @RequestParam(required = false) BigDecimal boxOffice, Model model) {
  model.addAttribute("budget", budget); model.addAttribute("boxOffice", boxOffice);
  if (budget != null || boxOffice != null) model.addAttribute("result", profitability.calculate(budget, boxOffice));
  return "services/profitability";
 }
 @GetMapping("/services/age-check")
 public String ageCheck(@RequestParam(required = false) Integer age, @RequestParam(required = false) Integer rating, Model model) {
  model.addAttribute("age", age); model.addAttribute("rating", rating); model.addAttribute("ratings", ageCheck.ratings());
  if (age != null || rating != null) {
   if (age == null || rating == null) throw new IllegalArgumentException("Заполните оба поля");
   model.addAttribute("allowed", ageCheck.check(age, rating));
  }
  return "services/age-check";
 }
 @GetMapping("/services/currency")
 public String currency(@RequestParam(required = false) BigDecimal amount, @RequestParam(defaultValue = "USD") String from,
   @RequestParam(defaultValue = "EUR") String to, Model model) {
  model.addAttribute("amount", amount); model.addAttribute("from", from); model.addAttribute("to", to);
  model.addAttribute("currencies", currency.currencies());
  if (amount != null) model.addAttribute("result", currency.convert(amount, from, to));
  return "services/currency";
 }
 @GetMapping("/services/weather")
 public String weather(@RequestParam(required = false) String city, Model model) {
  model.addAttribute("city", city);
  if (city != null) model.addAttribute("result", weather.current(city));
  return "services/weather";
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/controller/SiteSearchController.java](src/main/java/com/hellgardd/filmarchive/controller/SiteSearchController.java)

```java
package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.service.SiteSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SiteSearchController {
 private final SiteSearchService search;
 public SiteSearchController(SiteSearchService search) { this.search = search; }
 @GetMapping("/site-search")
 public String search(@RequestParam(defaultValue = "") String query, Model model) {
  model.addAttribute("query", query);
  model.addAttribute("results", search.search(query));
  return "site-search";
 }
}

```

#### [src/main/java/com/hellgardd/filmarchive/controller/CatalogController.java](src/main/java/com/hellgardd/filmarchive/controller/CatalogController.java)

```java
package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.dto.*;
import com.hellgardd.filmarchive.exception.FormValidationException;
import com.hellgardd.filmarchive.service.*;
import jakarta.validation.Valid;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogController {

 private final FilmService films;
 private final GenreService genres;
 private final CountryService countries;
 private final PersonService people;
 private final ReferenceService references;
 private final SearchService search;
 private final StatisticsService statistics;
 private final Clock clock;

 public CatalogController(
         FilmService films,
         GenreService genres,
         CountryService countries,
         PersonService people,
         ReferenceService references,
         SearchService search,
         StatisticsService statistics,
         Clock clock
 ) {
  this.films = films;
  this.genres = genres;
  this.countries = countries;
  this.people = people;
  this.references = references;
  this.search = search;
  this.statistics = statistics;
  this.clock = clock;
 }

 @GetMapping("/")
 public String index(Model model) {
  return "index";
 }

 private void options(Model model) {
  model.addAttribute("genres", genres.all());
  model.addAttribute("countries", countries.all());
  model.addAttribute("ageLimits", references.ages());
  model.addAttribute("qualities", references.qualities());
  model.addAttribute("people", people.all());
  model.addAttribute("today", LocalDate.now(clock));
 }

 private String editPage(Model model) {
  options(model);

  model.addAttribute("films", films.all());

  if (!model.containsAttribute("filmForm")) {
   model.addAttribute("filmForm", new FilmForm());
  }

  if (!model.containsAttribute("genreForm")) {
   model.addAttribute("genreForm", new ReferenceForm());
  }

  if (!model.containsAttribute("countryForm")) {
   model.addAttribute("countryForm", new ReferenceForm());
  }

  if (!model.containsAttribute("ageForm")) {
   model.addAttribute("ageForm", new AgeLimitForm());
  }

  if (!model.containsAttribute("qualityForm")) {
   model.addAttribute("qualityForm", new QualityForm());
  }

  if (!model.containsAttribute("personForm")) {
   model.addAttribute("personForm", new PersonForm());
  }

  return "edit";
 }

 @GetMapping("/catalog")
 public String catalog(Model model) {
  model.addAttribute("films", films.all());
  return "catalog";
 }

 @GetMapping("/edit")
 public String edit(Model model) {
  return editPage(model);
 }

 private void errors(
         BindingResult result,
         FormValidationException ex
 ) {
  ex.getErrors().forEach(
          (field, message) ->
                  result.rejectValue(field, "invalid", message)
  );
 }

 private String success(
         RedirectAttributes redirect,
         String message
 ) {
  redirect.addFlashAttribute("message", message);
  redirect.addFlashAttribute("messageType", "success");

  return "redirect:/edit";
 }

 private void warning(Model model) {
  model.addAttribute("message", "Проверьте поля формы");
  model.addAttribute("messageType", "warning");
 }

 @PostMapping("/add_genre")
 public String addGenre(
         @Valid @ModelAttribute("genreForm") ReferenceForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    genres.add(form);
    return success(redirect, "Жанр успешно добавлен");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_country")
 public String addCountry(
         @Valid @ModelAttribute("countryForm") ReferenceForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    countries.add(form);
    return success(redirect, "Страна успешно добавлена");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_quality")
 public String addQuality(
         @Valid @ModelAttribute("qualityForm") QualityForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    references.addQuality(form);
    return success(redirect, "Качество успешно добавлено");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_age_limit")
 public String addAge(
         @Valid @ModelAttribute("ageForm") AgeLimitForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    references.addAge(form);
    return success(
            redirect,
            "Возрастное ограничение успешно добавлено"
    );
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/add_person")
 public String addPerson(
         @Valid @ModelAttribute("personForm") PersonForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    people.add(form);
    return success(redirect, "Актёр успешно добавлен");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @PostMapping("/delete_person/{id}")
 public String deletePerson(
         @PathVariable Long id,
         RedirectAttributes redirect
 ) {
  people.delete(id);

  return success(
          redirect,
          "Актёр и его связи успешно удалены"
  );
 }

 @PostMapping("/add_film")
 public String addFilm(
         @Valid @ModelAttribute("filmForm") FilmForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  if (!result.hasErrors()) {
   try {
    films.add(form);
    return success(redirect, "Фильм успешно добавлен");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  warning(model);
  return editPage(model);
 }

 @GetMapping("/edit_film/{id}")
 public String editFilm(
         @PathVariable Long id,
         Model model
 ) {
  model.addAttribute("filmForm", films.form(id));
  model.addAttribute("filmId", id);

  options(model);

  return "edit-film";
 }

 @PostMapping("/update_film/{id}")
 public String updateFilm(
         @PathVariable Long id,
         @Valid @ModelAttribute("filmForm") FilmForm form,
         BindingResult result,
         Model model,
         RedirectAttributes redirect
 ) {
  films.get(id);

  if (!result.hasErrors()) {
   try {
    films.update(id, form);
    return success(redirect, "Фильм успешно обновлён");
   } catch (FormValidationException ex) {
    errors(result, ex);
   }
  }

  model.addAttribute("filmId", id);
  options(model);
  warning(model);

  return "edit-film";
 }

 @PostMapping("/delete_film/{id}")
 public String deleteFilm(
         @PathVariable Long id,
         RedirectAttributes redirect
 ) {
  films.delete(id);

  return success(
          redirect,
          "Фильм успешно удалён"
  );
 }

 @GetMapping("/search")
 public String search(
         @RequestParam(required = false) Long genre,
         @RequestParam(required = false) Long country,
         Model model
 ) {
  model.addAttribute("genres", genres.all());
  model.addAttribute("selectedGenre", genre);
  model.addAttribute("selectedCountry", country);

  model.addAttribute("countryOptions", List.of());
  model.addAttribute("results", List.of());
  model.addAttribute("searched", genre != null);

  try {
   if (genre != null) {
    model.addAttribute(
            "countryOptions",
            search.countries(genre)
    );

    model.addAttribute(
            "results",
            search.search(genre, country)
    );
   } else if (country != null) {
    throw new FormValidationException(
            "genre",
            "Сначала выберите жанр"
    );
   }
  } catch (FormValidationException ex) {
   model.addAttribute("message", ex.getMessage());
   model.addAttribute("messageType", "warning");
  }

  return "search";
 }

 @GetMapping("/get_countries")
 @ResponseBody
 public List<CountryOption> getCountries(
         @RequestParam Long genre
 ) {
  return search.countries(genre);
 }

 @GetMapping("/aggregate")
 public String aggregate(Model model) {
  model.addAttribute("stats", statistics.get());
  return "aggregate";
 }

 @GetMapping("/info")
 public String info() {
  return "info";
 }
}
```

#### [src/main/java/com/hellgardd/filmarchive/controller/GlobalExceptionHandler.java](src/main/java/com/hellgardd/filmarchive/controller/GlobalExceptionHandler.java)

```java
package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.exception.*;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.ui.Model;

import org.springframework.validation.BindException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

 private final Logger log =
         LoggerFactory.getLogger(GlobalExceptionHandler.class);

 private Object error(
         HttpServletRequest request,
         Model model,
         HttpStatus status,
         String message
 ) {
  if (request.getRequestURI().endsWith("/get_countries")) {
   return ResponseEntity
           .status(status)
           .body(Map.of("message", message));
  }

  model.addAttribute("noIndex", true);
  model.addAttribute("status", status.value());
  model.addAttribute("errorMessage", message);

  return "error";
 }

 @ExceptionHandler({
         NotFoundException.class,
         NoResourceFoundException.class
 })
 @ResponseStatus(HttpStatus.NOT_FOUND)
 public Object notFound(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  return error(
          request,
          model,
          HttpStatus.NOT_FOUND,
          "Запрошенная запись или страница не найдена"
  );
 }

 @ExceptionHandler({
         FormValidationException.class,
         BindException.class,
         ConstraintViolationException.class,
         MethodArgumentTypeMismatchException.class,
         MissingServletRequestParameterException.class,
         HttpMessageNotReadableException.class
 })
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public Object invalid(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  return error(
          request,
          model,
          HttpStatus.BAD_REQUEST,
          "Некорректные данные. Проверьте значения полей и повторите запрос."
  );
 }

 @ExceptionHandler(DataIntegrityViolationException.class)
 @ResponseStatus(HttpStatus.CONFLICT)
 public Object integrity(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  log.warn("Database constraint violation", ex);

  return error(
          request,
          model,
          HttpStatus.CONFLICT,
          "Данные уже существуют или связаны с другой записью. " +
                  "Обновите страницу и повторите действие."
  );
 }

 @ExceptionHandler({
         DataAccessException.class,
         PersistenceException.class
 })
 @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
 public Object database(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  log.error("Database operation failed", ex);

  return error(
          request,
          model,
          HttpStatus.SERVICE_UNAVAILABLE,
          "PostgreSQL временно недоступен. " +
                  "Проверьте подключение к базе данных."
  );
 }

 @ExceptionHandler(IllegalArgumentException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public Object input(IllegalArgumentException ex, HttpServletRequest request, Model model) {
  return error(request, model, HttpStatus.BAD_REQUEST, "Некорректные данные. Проверьте поля формы и повторите запрос.");
 }

 @ExceptionHandler(ExternalServiceException.class)
 @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
 public Object external(ExternalServiceException ex, HttpServletRequest request, Model model) {
  log.warn("External service failed", ex);
  return error(request, model, HttpStatus.SERVICE_UNAVAILABLE, "Сервис временно недоступен");
 }

 @ExceptionHandler(Exception.class)
 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
 public Object unexpected(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  log.error("Request failed", ex);

  return error(
          request,
          model,
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Не удалось выполнить запрос. Повторите попытку позже."
  );
 }
}
```

#### [src/main/java/com/hellgardd/filmarchive/config/SiteModelAdvice.java](src/main/java/com/hellgardd/filmarchive/config/SiteModelAdvice.java)

```java
package com.hellgardd.filmarchive.config;

import com.hellgardd.filmarchive.service.SeoService;
import com.hellgardd.filmarchive.service.SiteSearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SiteModelAdvice {
 private final SiteSearchService pages;
 private final SeoService seo;
 private final String email;
 private final String telegram;
 public SiteModelAdvice(SiteSearchService pages, SeoService seo, @Value("${site.contact.email}") String email,
   @Value("${site.contact.telegram}") String telegram) {
  this.pages = pages; this.seo = seo; this.email = email; this.telegram = telegram;
 }
 @ModelAttribute
 public void common(HttpServletRequest request, Model model) {
  String path = request.getRequestURI().substring(request.getContextPath().length());
  var page = pages.pages().stream().filter(p -> p.url().equals(path)).findFirst();
  model.addAttribute("pageDescription", page.map(p -> p.description()).orElse("FilmArchive — информационный портал о кино, фильмах, актёрах и кинематографе."));
  model.addAttribute("canonicalUrl", seo.canonical(path));
  model.addAttribute("noIndex", path.startsWith("/edit") || path.equals("/site-search") || path.equals("/error") || page.isEmpty() || request.getQueryString() != null);
  model.addAttribute("contactEmail", email);
  model.addAttribute("contactTelegram", telegram);
 }
}

```

### 4. HTML fragments

#### [src/main/resources/templates/fragments/header.html](src/main/resources/templates/fragments/header.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org">
<head th:fragment="head(title)">
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <title th:text="${title} + ' · FilmArchive'">FilmArchive</title>
 <meta name="description" th:content="${pageDescription ?: 'FilmArchive — информационный портал о кино, фильмах и актёрах.'}" content="FilmArchive — информационный портал о кино.">
 <meta name="robots" th:if="${noIndex}" content="noindex, follow">
 <link rel="canonical" th:if="${canonicalUrl != null and !noIndex}" th:href="${canonicalUrl}">
 <link rel="stylesheet" th:href="@{/css/style.css}">
 <script defer th:src="@{/js/main.js}"></script>
</head>
<body>
<header th:fragment="navigation" class="site-header">
 <div class="container nav-wrap">
  <a class="brand" th:href="@{/}"><span class="brand-mark" aria-hidden="true">▶</span> FilmArchive</a>
  <nav aria-label="Основная навигация">
   <a th:href="@{/}">Главная</a>
   <a th:href="@{/catalog}">Каталог</a>
   <a th:href="@{/search}">Поиск фильмов</a>
   <details class="nav-dropdown">
    <summary>Информационные страницы</summary>
    <div class="dropdown-items">
     <a th:href="@{/history}">История кинематографа</a>
     <a th:href="@{/genres-info}">Основные жанры кино</a>
     <a th:href="@{/cinema-technologies}">Технологии в кинематографе</a>
     <a th:href="@{/ratings-info}">Возрастные рейтинги</a>
     <a th:href="@{/formats}">Форматы и качество видео</a>
     <a th:href="@{/recommendations}">Как выбрать фильм</a>
    </div>
   </details>
   <details class="nav-dropdown">
    <summary>Сервисы</summary>
    <div class="dropdown-items">
     <a th:href="@{/services}">Все сервисы</a>
     <a th:href="@{/catalog}">Каталог фильмов</a>
     <a th:href="@{/search}">Поиск фильмов</a>
     <a th:href="@{/site-search}">Поиск по сайту</a>
     <a th:href="@{/aggregate}">Статистика каталога</a>
     <a th:href="@{/services/random-film}">Случайный фильм</a>
     <a th:href="@{/services/watch-time}">Калькулятор времени просмотра</a>
     <a th:href="@{/services/profitability}">Калькулятор окупаемости фильма</a>
     <a th:href="@{/services/age-check}">Проверка возрастного рейтинга</a>
     <a th:href="@{/services/currency}">Конвертер валют</a>
     <a th:href="@{/services/weather}">Погода</a>
    </div>
   </details>
   <a th:href="@{/aggregate}">Статистика</a>
   <a th:href="@{/links}">Полезные ссылки</a>
   <a th:href="@{/about}">О проекте</a>
   <a th:href="@{/contacts}">Контакты</a>
  </nav>
  <form class="site-search-form" method="get" th:action="@{/site-search}" role="search">
   <label for="global-query">Поиск по сайту</label>
   <div class="search-row">
    <input id="global-query" name="query" type="search" placeholder="Поиск по сайту…" maxlength="200" required th:value="${query}">
    <button type="submit">Найти</button>
   </div>
  </form>
 </div>
</header>
</body>
</html>

```

#### [src/main/resources/templates/fragments/footer.html](src/main/resources/templates/fragments/footer.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org">
<body>
<footer th:fragment="footer" class="site-footer">
 <div class="container footer-grid">
  <div><strong>FilmArchive — информационный портал о кино.</strong>
   <p>Сайт предназначен для пользователей, интересующихся кино, фильмами, актёрами и кинематографом.</p>
   <p>© 2026 FilmArchive</p>
  </div>
  <div><p>Разработчик: Даниил Сабадыш</p>
   <p>Email: <a th:href="${'mailto:' + contactEmail}" th:text="${contactEmail}">sabadyshdv23@oiate.ru</a></p>
   <p>Telegram: <a th:href="${'https://t.me/' + contactTelegram}" th:text="${'@' + contactTelegram}" target="_blank" rel="noopener noreferrer">@Hellgard</a></p>
   <a th:href="@{/contacts}">Контактная информация</a>
  </div>
 </div>
</footer>
</body>
</html>

```

#### [src/main/resources/templates/fragments/film-card.html](src/main/resources/templates/fragments/film-card.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org">
<body>
<article th:fragment="card(f)" class="panel film-detail">
 <h2 th:text="${f.title}">Название фильма</h2>
 <p th:text="${f.description}">Описание</p>
 <dl class="film-data">
  <div><dt>Жанр</dt><dd th:text="${f.genre.title}"></dd></div>
  <div><dt>Страна</dt><dd th:text="${f.country.title}"></dd></div>
  <div><dt>Длительность</dt><dd th:text="${f.duration} + ' мин'"></dd></div>
  <div><dt>Дата выхода</dt><dd th:text="${#temporals.format(f.releaseDate, 'dd.MM.yyyy')}"></dd></div>
  <div><dt>Возрастное ограничение</dt><dd th:text="${f.ageLimit.category}"></dd></div>
  <div><dt>Качество</dt><dd th:text="${f.shootingQuality.type}"></dd></div>
  <div><dt>Бюджет</dt><dd th:text="${#numbers.formatDecimal(f.budget, 1, 'WHITESPACE', 2, 'COMMA')}"></dd></div>
  <div><dt>Кассовые сборы</dt><dd th:text="${#numbers.formatDecimal(f.totalBoxOffice, 1, 'WHITESPACE', 2, 'COMMA')}"></dd></div>
  <div class="full-width"><dt>Актёры</dt><dd>
   <span th:if="${#sets.isEmpty(f.actors)}">Актёры пока не указаны</span>
   <ul th:unless="${#sets.isEmpty(f.actors)}"><li th:each="actor : ${f.actors}" th:text="${actor.fullName}"></li></ul>
  </dd></div>
 </dl>
</article>
</body>
</html>

```

### 5. Статические страницы и каталог

#### [src/main/resources/templates/index.html](src/main/resources/templates/index.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Главная'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<section class="hero">
 <div><p class="eyebrow">Информационный портал о кино</p><h1>Каждый фильм —<br>новая история.</h1>
 <p class="lead">Открывайте фильмы и актёров, изучайте жанры и историю кинематографа. FilmArchive помогает выбрать кино и узнать о нём больше.</p>
 <div class="actions"><a class="button" th:href="@{/catalog}">Открыть каталог</a><a class="button secondary" th:href="@{/services}">Все сервисы</a></div></div>
 <div class="hero-art" aria-hidden="true"><div class="film-card"><span>FILMARCHIVE</span><strong>Кино.<br>Истории.<br>Открытия.</strong><div class="film-stripe"></div><span>ВАШ ВЕЧЕР / ВАШ ВЫБОР</span></div></div>
</section>
<section class="feature-grid" aria-label="Разделы портала">
 <article class="panel"><h2>История кино</h2><p>От первых движущихся изображений до цифрового экрана.</p><a th:href="@{/history}">Читать</a></article>
 <article class="panel"><h2>Жанры и настроение</h2><p>Познакомьтесь с жанрами и найдите фильм на вечер.</p><a th:href="@{/genres-info}">Изучить жанры</a></article>
 <article class="panel"><h2>Практические инструменты</h2><p>Время просмотра, расчёты бюджета, валюты и погода.</p><a th:href="@{/services}">Открыть сервисы</a></article>
</section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/about.html](src/main/resources/templates/about.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='О проекте'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>О проекте</h1></div>
<section class="panel prose"><h2>Кино, которое хочется изучать</h2><p>FilmArchive — учебный информационный портал о кино. Он объединяет каталог фильмов, материалы о кинематографе и инструменты для планирования просмотра.</p><p>Сайт предназначен для пользователей, интересующихся фильмами, актёрами, жанрами, историей кино и дополнительной информацией о кинематографе.</p><h2>Что можно найти</h2><p>В каталоге доступны жанр, страна, длительность, дата выхода, возрастное ограничение, качество, актёры, бюджет и кассовые сборы. Поиск фильмов помогает выбрать жанр и страну, а поиск по сайту находит статьи и сервисы.</p><h2>Автор проекта</h2><p>Разработчик: Даниил Сабадыш. Вопросы и предложения можно отправить через <a th:href="@{/contacts}">контакты</a>.</p><p>Добавление и редактирование записей доступны на отдельной <a th:href="@{/edit}">странице управления</a>.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/history.html](src/main/resources/templates/history.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='История кинематографа'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>История кинематографа</h1></div>
<section class="panel prose"><h2>Движение на экране</h2><p>Кинематограф вырос из фотографии и экспериментов с последовательностью изображений. В конце XIX века появились устройства, позволяющие снимать и показывать движущиеся изображения аудитории.</p><h2>Немое кино</h2><p>Первые фильмы были короткими. Постепенно авторы освоили монтаж, крупный план и визуальное повествование. Музыканты сопровождали сеансы, а титры помогали передавать реплики и пояснения.</p><h2>Звук и цвет</h2><p>Синхронная запись звука изменила актёрскую игру и работу над фильмом. Цвет расширил выразительные средства, а широкоформатный экран сделал кинопоказ более зрелищным.</p><h2>Цифровая эпоха</h2><p>Цифровая съёмка, компьютерные эффекты и онлайн-платформы изменили производство и распространение фильмов. При этом работа с историей, образами и ритмом монтажа остаётся основой кино.</p><p>Продолжить знакомство с архивами можно на странице <a th:href="@{/links}">полезных ресурсов</a>.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/genres-info.html](src/main/resources/templates/genres-info.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Основные жанры кино'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Основные жанры кино</h1></div>
<section class="panel prose"><h2>Жанр помогает выбрать настроение</h2><p>Жанры описывают характерные темы, конфликты и способы повествования. Один фильм может сочетать признаки нескольких жанров.</p><dl><dt>Драма</dt><dd>Сосредоточена на переживаниях персонажей, отношениях и сложных жизненных решениях.</dd><dt>Комедия</dt><dd>Использует юмор, неожиданные ситуации, сатиру и игру с ожиданиями зрителя.</dd><dt>Фантастика</dt><dd>Исследует необычные миры, технологии, возможное будущее и последствия научных открытий.</dd><dt>Боевик</dt><dd>Строится вокруг динамичного действия, противостояния и опасных испытаний.</dd><dt>Триллер</dt><dd>Поддерживает напряжение, неопределённость и ожидание развязки.</dd><dt>Документальное кино</dt><dd>Рассказывает о реальных людях и событиях с помощью наблюдения, интервью и архивных материалов.</dd><dt>Анимация</dt><dd>Способ создания движущихся изображений; анимационный фильм может относиться к любому жанру.</dd></dl><p>Попробуйте <a th:href="@{/search}">поиск фильмов по жанру</a>.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/cinema-technologies.html](src/main/resources/templates/cinema-technologies.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Технологии в кинематографе'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Технологии в кинематографе</h1></div>
<section class="panel prose"><h2>Съёмка и свет</h2><p>Камера фиксирует изображение, а объектив, освещение и композиция определяют его выразительность. Плёночная и цифровая съёмка предлагают разные способы работы с материалом.</p><h2>Монтаж</h2><p>Монтаж соединяет планы в историю. Порядок и длительность кадров помогают управлять вниманием, временем и эмоциями зрителя.</p><h2>Звук</h2><p>Диалоги, музыка, шумы и тишина формируют звуковое пространство фильма. Многоканальный звук помогает передать положение источников звука вокруг зрителя.</p><h2>Визуальные эффекты</h2><p>Практические эффекты создаются на площадке, цифровые — при обработке материала. Компьютерная графика позволяет строить окружение и персонажей, которых сложно снять напрямую.</p><h2>Показ</h2><p>Цифровые проекторы и системы управления цветом помогают воспроизводить замысел авторов. Итоговое впечатление зависит от экрана, звука и условий просмотра.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/ratings-info.html](src/main/resources/templates/ratings-info.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Возрастные рейтинги'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Возрастные рейтинги</h1></div>
<section class="panel prose"><h2>Что означает отметка возраста</h2><p>Возрастная маркировка помогает учитывать содержание фильма при выборе просмотра. В каталоге FilmArchive используются обозначения 0+, 6+, 12+, 16+ и 18+.</p><ul><li>0+ — категория без возрастного ограничения в используемом справочнике.</li><li>6+ — возрастной порог 6 лет.</li><li>12+ — возрастной порог 12 лет.</li><li>16+ — возрастной порог 16 лет.</li><li>18+ — возрастной порог 18 лет.</li></ul><h2>Как работает проверка на сайте</h2><p>Учебный сервис сравнивает введённый возраст с числом в обозначении. Он не анализирует содержание фильма. Перед просмотром изучите описание и официальную маркировку конкретного издания.</p><p><a th:href="@{/services/age-check}">Проверить возраст и рейтинг</a></p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/formats.html](src/main/resources/templates/formats.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Форматы и качество видео'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Форматы и качество видео</h1></div>
<section class="panel prose"><h2>Разрешение</h2><p>HD обычно обозначает изображение 1280 × 720 пикселей, Full HD — 1920 × 1080, а 4K UHD — 3840 × 2160. Более высокое разрешение может передать больше деталей при подходящем экране и хорошем исходном материале.</p><h2>Контейнер и кодек</h2><p>Контейнер, например MP4 или MKV, объединяет видео, звуковые дорожки и субтитры. Кодек, например H.264, H.265 или AV1, определяет способ сжатия видеоданных.</p><h2>Битрейт, цвет и частота кадров</h2><p>Качество зависит не только от числа пикселей. Сильное сжатие может создавать заметные артефакты. Цветовой диапазон, контраст и частота кадров также влияют на результат.</p><h2>Качество в каталоге</h2><p>Справочник качества хранит обозначение, выбранное при добавлении фильма. Это характеристика записи каталога, а не результат анализа видеофайла.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/recommendations.html](src/main/resources/templates/recommendations.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Как выбрать фильм'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Как выбрать фильм</h1></div>
<section class="panel prose"><h2>Начните с настроения</h2><p>Подумайте, хотите ли вы посмеяться, погрузиться в драму, открыть необычный мир или узнать о реальном событии. Затем выберите подходящий жанр.</p><h2>Проверьте доступное время</h2><p>Сравните длительность фильма со своими планами. Если собираетесь смотреть несколько фильмов, <a th:href="@{/services/watch-time}">калькулятор времени</a> поможет оценить продолжительность вечера.</p><h2>Учитывайте компанию</h2><p>Для совместного просмотра обсудите предпочтения и изучите возрастную маркировку. Краткое описание часто полезнее случайного фрагмента трейлера.</p><h2>Используйте несколько источников</h2><p>Рецензии помогают понять сильные стороны фильма, но оценки отражают разные вкусы. Сопоставьте мнения зрителей и критиков со своими интересами.</p><h2>Дайте шанс неожиданному выбору</h2><p>Если решение затянулось, попробуйте <a th:href="@{/services/random-film}">случайный фильм</a> из каталога.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/links.html](src/main/resources/templates/links.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Полезные ресурсы о кино'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Полезные ресурсы о кино</h1></div>
<section class="panel prose"><p>Знакомьтесь с фильмами, читайте рецензии и изучайте архивные материалы на тематических сайтах.</p><div class="resource-grid"><article><h2><a href="https://www.imdb.com/" target="_blank" rel="noopener noreferrer">IMDb</a></h2><p>База фильмов, актёров и участников съёмочных групп.</p></article>
<article><h2><a href="https://www.themoviedb.org/" target="_blank" rel="noopener noreferrer">The Movie Database</a></h2><p>Сообщество и база сведений о фильмах и сериалах.</p></article>
<article><h2><a href="https://www.rottentomatoes.com/" target="_blank" rel="noopener noreferrer">Rotten Tomatoes</a></h2><p>Обзоры и агрегированные оценки критиков и зрителей.</p></article>
<article><h2><a href="https://www.metacritic.com/movie/" target="_blank" rel="noopener noreferrer">Metacritic</a></h2><p>Сводные оценки и ссылки на рецензии.</p></article>
<article><h2><a href="https://www.bfi.org.uk/" target="_blank" rel="noopener noreferrer">British Film Institute</a></h2><p>Материалы о кинематографе, культурном наследии и кинообразовании.</p></article>
<article><h2><a href="https://archive.org/details/movies" target="_blank" rel="noopener noreferrer">Internet Archive</a></h2><p>Архив видеоматериалов и исторических коллекций.</p></article>
<article><h2><a href="https://www.kinopoisk.ru/" target="_blank" rel="noopener noreferrer">Кинопоиск</a></h2><p>Русскоязычная база фильмов, актёров, рецензий и подборок.</p></article>
<article><h2><a href="https://www.pixar.com/" target="_blank" rel="noopener noreferrer">Pixar</a></h2><p>Официальный сайт анимационной студии и её фильмов.</p></article></div></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/contacts.html](src/main/resources/templates/contacts.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Контактная информация'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Контактная информация</h1></div>
<section class="panel prose"><h2>Разработчик</h2><p>Даниил Сабадыш — разработчик учебного проекта FilmArchive.</p><h2>Обратная связь</h2><p>Email: <a th:href="${'mailto:' + contactEmail}" th:text="${contactEmail}">sabadyshdv23@oiate.ru</a></p><p>Telegram: <a th:href="${'https://t.me/' + contactTelegram}" th:text="${'@' + contactTelegram}" target="_blank" rel="noopener noreferrer">@Hellgard</a></p><p>По этим контактам можно сообщить об ошибке, предложить улучшение сайта или задать вопрос о проекте.</p><h2>О сайте</h2><p>FilmArchive — информационный портал для пользователей, интересующихся фильмами, актёрами, жанрами, историей кино и кинематографом.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/catalog.html](src/main/resources/templates/catalog.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Каталог фильмов'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><div><h1>Каталог фильмов</h1><p>Фильмы и актёры из коллекции FilmArchive.</p></div><a class="button secondary" th:href="@{/search}">Поиск по жанру и стране</a></div>
<p class="muted">Бюджеты и сборы показаны в денежных единицах, использованных при заполнении каталога; валюта в текущей модели данных отдельно не хранится.</p>
<p class="empty-state" th:if="${#lists.isEmpty(films)}">Каталог пока пуст.</p>
<section class="catalog-grid" aria-label="Фильмы"><div th:each="f : ${films}"><article th:replace="~{fragments/film-card :: card(${f})}"></article></div></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/edit.html](src/main/resources/templates/edit.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Управление'">
  <head th:replace="~{fragments/header :: head(${pageTitle})}">
  </head>
  <body>
    <header th:replace="~{fragments/header :: navigation}">
    </header>
    <main class="container">
      <div th:replace="~{fragments/messages :: messages}">
      </div>
      <div class="page-heading">
        <div>
          <p class="eyebrow">Ваша коллекция</p>
          <h1>Управление данными</h1>
          <p class="muted">Добавляйте фильмы, редактируйте карточки и пополняйте справочники.</p>
        </div>
        <a class="button" href="#add-film">Добавить фильм</a>
      </div>
      <nav class="section-nav" aria-label="Разделы управления">
        <a href="#films">Фильмы</a>
        <a href="#add-film">Новый фильм</a>
        <a href="#genres">Жанры</a>
        <a href="#countries">Страны</a>
        <a href="#ages">Возраст</a>
        <a href="#qualities">Качество</a>
        <a href="#people">Актёры</a>
      </nav>
      <section class="panel" id="films">
        <div class="section-heading">
          <h2>Фильмы</h2>
          <span class="badge" th:text="${#lists.size(films)} + ' в коллекции'">
          </span>
        </div>
        <div class="table-scroll" th:unless="${#lists.isEmpty(films)}">
          <table>
            <thead>
              <tr>
                <th>Название</th>
                <th>Жанр / страна</th>
                <th>Возраст / качество</th>
                <th>Бюджет, USD</th>
                <th>Сборы, USD</th>
                <th>Актёры</th>
                <th>Действия</th>
              </tr>
            </thead>
            <tbody>
              <tr th:each="f : ${films}">
                <td>
                  <strong th:text="${f.title}">
                  </strong>
                  <small th:text="${#temporals.format(f.releaseDate, 'dd.MM.yyyy')}">
                  </small>
                  <small th:text="${f.duration} + ' мин'">
                  </small>
                  <details th:if="${!#strings.isEmpty(f.description)}">
                    <summary>Описание</summary>
                    <p th:text="${f.description}">
                    </p>
                  </details>
                </td>
                <td>
                  <span th:text="${f.genre.title}">
                  </span>
                  <small th:text="${f.country.title}">
                  </small>
                </td>
                <td>
                  <span class="badge" th:text="${f.ageLimit.category}">
                  </span>
                  <small th:text="${f.shootingQuality.type}">
                  </small>
                </td>
                <td class="numeric" th:text="${#numbers.formatDecimal(f.budget,1,'WHITESPACE',2,'COMMA')}">
                </td>
                <td class="numeric" th:text="${#numbers.formatDecimal(f.totalBoxOffice,1,'WHITESPACE',2,'COMMA')}">
                </td>
                <td class="actors-cell" th:text="${f.actorNames}">
                </td>
                <td>
                  <div class="row-actions">
                    <a class="button secondary small" th:href="@{/edit_film/{id}(id=${f.id})}">Изменить</a>
                    <form method="post" th:action="@{/delete_film/{id}(id=${f.id})}" data-confirm="Удалить фильм? Актёры останутся в каталоге.">
                      <button class="danger small" type="submit">Удалить</button>
                    </form>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <p class="empty-state" th:if="${#lists.isEmpty(films)}">В каталоге пока нет фильмов. Добавьте первый фильм с помощью формы ниже.</p>
      </section>
      <section class="panel form-panel" id="add-film">
        <p class="eyebrow">Новая история</p>
        <h2>Добавить фильм</h2>
        <p class="muted">Поля со звёздочкой обязательны. Все денежные суммы указаны в долларах США.</p>
        <form method="post" th:action="@{/add_film}" th:object="${filmForm}">
          <div th:replace="~{fragments/film-fields :: fields}">
          </div>
          <div class="actions">
            <button type="submit">Добавить</button>
            <a class="button secondary" th:href="@{/edit}">Отмена</a>
          </div>
        </form>
      </section>
      <div class="reference-grid">
        <section id="genres" class="panel reference-panel">
          <h2>Жанры</h2>
          <form method="post" th:action="@{/add_genre}" th:object="${genreForm}">
            <ul class="validation-summary" th:if="${#fields.hasErrors('*')}" role="alert">
              <li th:each="err : ${#fields.errors('*')}" th:text="${err}">
              </li>
            </ul>
            <div class="compact-form">
              <label>Название<input id="genre-title" th:field="*{title}" maxlength="120" required>
              </label>
              <label>Код<input id="genre-code" th:field="*{code}" maxlength="120" required>
              </label>
              <button type="submit">Добавить</button>
            </div>
          </form>
          <ul class="reference-list">
            <li th:each="item : ${genres}">
              <span>
                <strong th:text="${item.title}">
                </strong>
                <small th:text="${item.code}">
                </small>
              </span>
            </li>
          </ul>
          <p class="muted" th:if="${#lists.isEmpty(genres)}">Пока нет записей.</p>
        </section>
        <section id="countries" class="panel reference-panel">
          <h2>Страны</h2>
          <form method="post" th:action="@{/add_country}" th:object="${countryForm}">
            <ul class="validation-summary" th:if="${#fields.hasErrors('*')}" role="alert">
              <li th:each="err : ${#fields.errors('*')}" th:text="${err}">
              </li>
            </ul>
            <div class="compact-form">
              <label>Название<input id="country-title" th:field="*{title}" maxlength="120" required>
              </label>
              <label>Код<input id="country-code" th:field="*{code}" maxlength="120" required>
              </label>
              <button type="submit">Добавить</button>
            </div>
          </form>
          <ul class="reference-list">
            <li th:each="item : ${countries}">
              <span>
                <strong th:text="${item.title}">
                </strong>
                <small th:text="${item.code}">
                </small>
              </span>
            </li>
          </ul>
          <p class="muted" th:if="${#lists.isEmpty(countries)}">Пока нет записей.</p>
        </section>
        <section id="ages" class="panel reference-panel">
          <h2>Возрастные ограничения</h2>
          <form method="post" th:action="@{/add_age_limit}" th:object="${ageForm}">
            <ul class="validation-summary" th:if="${#fields.hasErrors('*')}" role="alert">
              <li th:each="err : ${#fields.errors('*')}" th:text="${err}">
              </li>
            </ul>
            <div class="compact-form">
              <label>Категория<input id="age-category" th:field="*{category}" maxlength="120" required>
              </label>
              <label>Код<input id="age-code" th:field="*{code}" maxlength="120" required>
              </label>
              <button type="submit">Добавить</button>
            </div>
          </form>
          <ul class="reference-list">
            <li th:each="item : ${ageLimits}">
              <span>
                <strong th:text="${item.category}">
                </strong>
                <small th:text="${item.code}">
                </small>
              </span>
            </li>
          </ul>
          <p class="muted" th:if="${#lists.isEmpty(ageLimits)}">Пока нет записей.</p>
        </section>
        <section id="qualities" class="panel reference-panel">
          <h2>Качество съёмки</h2>
          <form method="post" th:action="@{/add_quality}" th:object="${qualityForm}">
            <ul class="validation-summary" th:if="${#fields.hasErrors('*')}" role="alert">
              <li th:each="err : ${#fields.errors('*')}" th:text="${err}">
              </li>
            </ul>
            <div class="compact-form">
              <label>Название<input id="quality-type" th:field="*{type}" maxlength="120" required>
              </label>
              <button type="submit">Добавить</button>
            </div>
          </form>
          <ul class="reference-list">
            <li th:each="item : ${qualities}">
              <span>
                <strong th:text="${item.type}">
                </strong>
              </span>
            </li>
          </ul>
          <p class="muted" th:if="${#lists.isEmpty(qualities)}">Пока нет записей.</p>
        </section>
        <section id="people" class="panel reference-panel">
          <h2>Актёры</h2>
          <form method="post" th:action="@{/add_person}" th:object="${personForm}">
            <ul class="validation-summary" th:if="${#fields.hasErrors('*')}" role="alert">
              <li th:each="err : ${#fields.errors('*')}" th:text="${err}">
              </li>
            </ul>
            <div class="compact-form">
              <label>ФИО<input id="person-fullName" th:field="*{fullName}" maxlength="120" required>
              </label>
              <button type="submit">Добавить</button>
            </div>
          </form>
          <ul class="reference-list">
            <li th:each="item : ${people}">
              <span>
                <strong th:text="${item.fullName}">
                </strong>
              </span>
              <form method="post" th:action="@{/delete_person/{id}(id=${item.id})}" data-confirm="Удалить актёра и его связи с фильмами? Фильмы сохранятся.">
                <button class="danger small" type="submit">Удалить</button>
              </form>
            </li>
          </ul>
          <p class="muted" th:if="${#lists.isEmpty(people)}">Пока нет записей.</p>
        </section>
      </div>
    </main>
    <footer th:replace="~{fragments/footer :: footer}">
    </footer>
  </body>
</html>

```

### 6. Страницы сервисов

#### [src/main/resources/templates/site-search.html](src/main/resources/templates/site-search.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Поиск по сайту'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Поиск по сайту</h1></div>
<section class="panel prose"><h2>Информационные страницы и сервисы</h2>
<p>Введите запрос в поле поиска в верхнем меню. Для поиска фильмов по жанру и стране используйте <a th:href="@{/search}">поиск фильмов</a>.</p>
<p th:if="${#strings.isEmpty(query)}">Например: история, жанры, погода.</p>
<div th:unless="${#strings.isEmpty(query)}"><p>Запрос: <strong th:text="${query}"></strong></p>
<p th:if="${#lists.isEmpty(results)}">По вашему запросу ничего не найдено.</p>
<article class="search-result" th:each="page : ${results}"><h3><a th:href="@{${page.url}}" th:text="${page.title}"></a></h3><p th:text="${page.description}"></p><p class="muted" th:text="${page.url}"></p></article></div></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services.html](src/main/resources/templates/services.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Сервисы'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><div><h1>Сервисы FilmArchive</h1><p>Десять инструментов для работы с каталогом и планирования просмотра.</p></div></div>
<section class="feature-grid" aria-label="Сервисы"><article class="panel" th:each="service : ${services}"><h2 th:text="${service.title}"></h2><p th:text="${service.description}"></p><a class="button secondary" th:href="@{${service.url}}">Открыть</a></article></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services/random-film.html](src/main/resources/templates/services/random-film.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Случайный фильм'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Случайный фильм</h1><a class="button" th:href="@{/services/random-film}">Выбрать ещё раз</a></div>
<section th:if="${film != null}" aria-label="Выбранный фильм"><article th:replace="~{fragments/film-card :: card(${film})}"></article></section>
<p class="empty-state" th:if="${film == null}">В каталоге пока нет фильмов. Добавьте записи на странице управления.</p>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services/watch-time.html](src/main/resources/templates/services/watch-time.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Калькулятор времени просмотра'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Калькулятор времени просмотра</h1></div>
<section class="panel prose"><h2>Сколько займёт киномарафон</h2><form method="get" th:action="@{/services/watch-time}" class="tool-form" data-calculator="watch-time">
<label for="film-count">Количество фильмов</label><input id="film-count" name="count" type="number" min="1" max="10000" step="1" required th:value="${count}">
<label for="average-duration">Средняя длительность, минут</label><input id="average-duration" name="duration" type="number" min="1" max="1440" step="1" required th:value="${duration}">
<button type="submit">Рассчитать</button><output class="live-preview" aria-live="polite"></output></form>
<div class="result-box" th:if="${result != null}" role="status"><p>Общее время: <strong th:text="${result.minutes} + ' мин'"></strong></p><p th:text="${result.hours} + ' ч ' + ${result.remainingMinutes} + ' мин'"></p></div>
<p class="muted">Расчёт не включает перерывы. Форма работает и при отключённом JavaScript.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services/profitability.html](src/main/resources/templates/services/profitability.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Калькулятор окупаемости фильма'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Калькулятор окупаемости фильма</h1></div>
<section class="panel prose"><h2>Бюджет и кассовые сборы</h2><form method="get" th:action="@{/services/profitability}" class="tool-form" data-calculator="profitability">
<label for="film-budget">Бюджет</label><input id="film-budget" name="budget" type="number" min="0.01" max="1000000000000" step="0.01" required th:value="${budget}">
<label for="film-box-office">Кассовые сборы</label><input id="film-box-office" name="boxOffice" type="number" min="0" max="1000000000000" step="0.01" required th:value="${boxOffice}">
<button type="submit">Рассчитать</button><output class="live-preview" aria-live="polite"></output></form>
<div class="result-box" th:if="${result != null}" role="status"><p>Коэффициент сборы / бюджет: <strong th:text="${result.ratio}"></strong></p><p>Прибыль по формуле сборы − бюджет: <strong th:text="${result.profit}"></strong></p></div>
<p class="muted">Укажите суммы в одной валюте. Учебная формула не учитывает долю кинотеатров, маркетинг, налоги и другие расходы.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services/age-check.html](src/main/resources/templates/services/age-check.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Проверка возрастного рейтинга'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Проверка возрастного рейтинга</h1></div>
<section class="panel prose"><h2>Возраст зрителя и отметка фильма</h2><form method="get" th:action="@{/services/age-check}" class="tool-form">
<label for="viewer-age">Возраст зрителя</label><input id="viewer-age" name="age" type="number" min="0" max="120" step="1" required th:value="${age}">
<label for="film-rating">Возрастной рейтинг</label><select id="film-rating" name="rating" required><option value="">Выберите рейтинг</option><option th:each="r : ${ratings}" th:value="${r}" th:text="${r} + '+'" th:selected="${r == rating}"></option></select>
<button type="submit">Проверить</button></form>
<div class="result-box" th:if="${allowed != null}" role="status"><p th:text="${allowed} ? 'Возраст соответствует выбранному рейтингу.' : 'Возраст не соответствует выбранному рейтингу.'"></p></div>
<p>Сервис сравнивает числа. Подробнее: <a th:href="@{/ratings-info}">возрастные рейтинги</a>.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services/currency.html](src/main/resources/templates/services/currency.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Конвертер валют'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Конвертер валют</h1></div>
<section class="panel prose"><h2>Пересчёт суммы по справочному курсу</h2><form method="get" th:action="@{/services/currency}" class="tool-form">
<label for="currency-amount">Сумма</label><input id="currency-amount" name="amount" type="number" min="0" max="1000000000000" step="0.01" required th:value="${amount}">
<label for="currency-from">Из валюты</label><select id="currency-from" name="from" required><option th:each="c : ${currencies}" th:value="${c}" th:text="${c}" th:selected="${c == from}"></option></select>
<label for="currency-to">В валюту</label><select id="currency-to" name="to" required><option th:each="c : ${currencies}" th:value="${c}" th:text="${c}" th:selected="${c == to}"></option></select>
<button type="submit">Конвертировать</button></form>
<div class="result-box" th:if="${result != null}" role="status"><p><strong th:text="${result.amount} + ' ' + ${result.from} + ' = ' + ${result.converted} + ' ' + ${result.to}"></strong></p><p>Курс: <span th:text="${result.rate}"></span></p><p th:if="${result.from != result.to}">Дата курса: <span th:text="${result.date}"></span></p><p th:if="${result.from == result.to}">При совпадении валют сумма не меняется.</p></div>
<p class="muted">Справочные данные: <a href="https://frankfurter.dev/" target="_blank" rel="noopener noreferrer">Frankfurter</a>. Курс не включает комиссии обмена.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

#### [src/main/resources/templates/services/weather.html](src/main/resources/templates/services/weather.html)

```html
<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org" th:with="pageTitle='Погода'">
<head th:replace="~{fragments/header :: head(${pageTitle})}"></head>
<body>
<header th:replace="~{fragments/header :: navigation}"></header>
<main class="container" id="main-content">
<div class="page-heading"><h1>Погода перед походом в кино</h1></div>
<section class="panel prose"><h2>Текущие условия в городе</h2><form method="get" th:action="@{/services/weather}" class="tool-form">
<label for="weather-city">Город</label><input id="weather-city" name="city" type="text" minlength="2" maxlength="100" placeholder="Москва" required th:value="${city}">
<button type="submit">Показать погоду</button></form>
<div class="result-box" th:if="${result != null}" role="status"><h3 th:text="${result.city} + ', ' + ${result.country}"></h3><p>Температура: <strong th:text="${result.temperature} + ' °C'"></strong></p><p>Ощущается как: <span th:text="${result.feelsLike} + ' °C'"></span></p><p th:text="${result.description}"></p><p>Время данных в городе: <span th:text="${result.observedAt}"></span></p></div>
<p class="muted">При совпадении названий выбирается первый результат поиска. Данные: <a href="https://open-meteo.com/" target="_blank" rel="noopener noreferrer">Open-Meteo</a>, поиск города — <a href="https://www.geonames.org/" target="_blank" rel="noopener noreferrer">GeoNames</a>.</p></section>
</main>
<footer th:replace="~{fragments/footer :: footer}"></footer>
</body>
</html>

```

### 7. CSS

#### [src/main/resources/static/css/style.css](src/main/resources/static/css/style.css)

```css
:root {
  --bg:#f5f4ef;
  --paper:#fff;
  --ink:#192c2a;
  --muted:#65716b;
  --line:#dedfd7;
  --accent:#206958;
  --accent-light:#e7f0e9;
  --danger:#a32e2e;
  --radius:16px;
}
* {
  box-sizing:border-box;
}
html {
  scroll-behavior:smooth;
  scroll-padding-top:100px;
}
body {
  margin:0;
  background:var(--bg);
  color:var(--ink);
  font-family:system-ui,-apple-system,"Segoe UI",sans-serif;
  font-size:15px;
  line-height:1.6;
}
a {
  color:var(--accent);
  text-decoration:none;
}
a:hover {
  text-decoration:underline;
}
.container {
  width:min(1440px,94%);
  margin:auto;
}
.site-header {
  background:var(--paper);
  border-bottom:1px solid var(--line);
}
.nav-wrap {
  min-height:86px;
  display:flex;
  align-items:center;
  justify-content:space-between;
  gap:24px;
}
.brand {
  font-size:20px;
  font-weight:750;
  color:var(--ink);
  display:flex;
  align-items:center;
  gap:12px;
}
.brand-mark {
  display:grid;
  place-items:center;
  background:var(--accent);
  color:white;
  width:36px;
  height:36px;
  border-radius:10px;
  font-size:15px;
}
nav {
  display:flex;
  gap:8px;
  flex-wrap:wrap;
}
.site-header nav a {
  padding:9px 13px;
  color:var(--muted);
  border-radius:8px;
}
.site-header nav a:hover,.site-header nav a[aria-current=page] {
  background:var(--accent-light);
  color:var(--accent);
  text-decoration:none;
}
main {
  min-height:70vh;
  padding-top:34px;
  padding-bottom:60px;
}
h1,h2,h3,p {
  margin-top:0;
}
h1 {
  font-size:clamp(30px,3vw,46px);
  line-height:1.15;
  letter-spacing:-1.5px;
  margin-bottom:18px;
}
h2 {
  font-size:23px;
  letter-spacing:-.5px;
  margin-bottom:18px;
}
.muted,small {
  color:var(--muted);
}
.eyebrow {
  text-transform:uppercase;
  letter-spacing:2px;
  font-size:11px;
  font-weight:750;
  color:var(--accent);
  margin-bottom:16px;
}
.hero {
  display:grid;
  grid-template-columns:1.2fr 1fr;
  align-items:center;
  gap:65px;
  padding:36px 0 60px;
}
.hero h1 {
  font-size:clamp(40px,4.4vw,67px);
}
.lead {
  font-size:18px;
  max-width:580px;
  color:var(--muted);
}
.actions {
  display:flex;
  gap:12px;
  flex-wrap:wrap;
  margin-top:24px;
}
.button,button {
  display:inline-flex;
  justify-content:center;
  align-items:center;
  gap:12px;
  background:var(--accent);
  color:#fff;
  border:1px solid transparent;
  border-radius:9px;
  padding:12px 20px;
  cursor:pointer;
  line-height:1.4;
  font:inherit;
  font-weight:600;
  white-space:nowrap;
}
.button:hover,button:hover {
  filter:brightness(.94);
  text-decoration:none;
}
.secondary {
  background:transparent;
  border-color:var(--line);
  color:var(--ink);
}
.danger {
  background:#fff0ee;
  color:var(--danger);
  border-color:#edd1cd;
}
.small {
  padding:7px 11px;
  font-size:12px;
}
.hero-art {
  min-height:350px;
  background:#d8e4db;
  border-radius:28px;
  display:grid;
  place-items:center;
  overflow:hidden;
  position:relative;
}
.hero-art:before,.hero-art:after {
  content:"";
  position:absolute;
  border:1px solid #a8c0af;
  border-radius:50%;
  width:430px;
  height:430px;
}
.hero-art:after {
  width:330px;
  height:330px;
}
.film-card {
  z-index:1;
  width:265px;
  height:300px;
  background:#1f453b;
  color:#fff;
  border:10px solid #fdfcf6;
  box-shadow:16px 18px 0 #102e2420;
  transform:rotate(-7deg);
  border-radius:3px;
  padding:24px;
  display:flex;
  flex-direction:column;
  justify-content:space-between;
}
.film-card>span {
  font-size:10px;
  letter-spacing:3px;
  color:#b8d2c0;
}
.film-card strong {
  font-size:26px;
  line-height:1.3;
}
.film-stripe {
  height:34px;
  background:repeating-linear-gradient(90deg,#b9d3ba 0 20px,transparent 20px 30px);
  opacity:.65;
}
.stats-grid {
  display:grid;
  grid-template-columns:repeat(4,minmax(0,1fr));
  gap:16px;
  margin-bottom:30px;
}
.stat {
  background:var(--paper);
  border:1px solid var(--line);
  border-radius:var(--radius);
  padding:24px;
}
.stat span {
  color:var(--muted);
  display:block;
}
.stat strong {
  font-size:40px;
  font-weight:650;
  display:block;
  line-height:1.4;
  margin-top:8px;
}
.panel {
  background:var(--paper);
  border:1px solid var(--line);
  border-radius:var(--radius);
  padding:28px;
  margin-bottom:24px;
}
.feature-grid {
  display:grid;
  grid-template-columns:repeat(3,1fr);
  gap:20px;
}
.feature {
  color:var(--ink);
  margin-bottom:0;
}
.feature:hover {
  border-color:var(--accent);
  text-decoration:none;
}
.feature p {
  color:var(--muted);
  margin-bottom:0;
}
.feature .eyebrow {
  display:block;
}
.page-heading {
  display:flex;
  justify-content:space-between;
  align-items:center;
  gap:24px;
  margin:10px 0 30px;
}
.page-heading p:last-child {
  margin-bottom:0;
}
.section-nav {
  margin-bottom:24px;
}
.section-nav a {
  padding:7px 15px;
  border:1px solid var(--line);
  border-radius:30px;
  color:var(--ink);
}
.section-heading {
  display:flex;
  align-items:center;
  justify-content:space-between;
  gap:16px;
  margin-bottom:16px;
}
.section-heading h2 {
  margin:0;
}
.badge {
  display:inline-block;
  background:var(--accent-light);
  color:var(--accent);
  border-radius:6px;
  padding:3px 9px;
  font-size:12px;
  white-space:nowrap;
}
.table-scroll {
  overflow-x:auto;
}
table {
  width:100%;
  border-collapse:collapse;
  font-size:13px;
}
th {
  text-align:left;
  font-weight:600;
  color:var(--muted);
  background:#f8f9f5;
  white-space:nowrap;
}
th,td {
  padding:16px 12px;
  border-bottom:1px solid var(--line);
  vertical-align:top;
}
tbody tr:last-child td {
  border-bottom:0;
}
td small {
  display:block;
  margin-top:3px;
}
.numeric {
  white-space:nowrap;
  font-variant-numeric:tabular-nums;
}
.actors-cell {
  min-width:180px;
  white-space:nowrap;
}
.row-actions {
  display:flex;
  gap:7px;
  flex-wrap:wrap;
}
details p {
  min-width:180px;
  max-width:360px;
  margin:8px 0 0;
}
summary {
  color:var(--accent);
  cursor:pointer;
  margin-top:5px;
}
.form-panel {
  max-width:1020px;
}
.form-grid {
  display:grid;
  grid-template-columns:repeat(2,minmax(0,1fr));
  gap:20px;
}
.wide {
  grid-column:1/-1;
}
label {
  display:block;
  font-size:13px;
  font-weight:650;
  color:var(--ink);
}
input,select,textarea {
  font:inherit;
  font-weight:400;
  width:100%;
  padding:11px 12px;
  margin-top:7px;
  background:#fff;
  border:1px solid #cdd5ce;
  border-radius:8px;
  color:var(--ink);
}
textarea {
  resize:vertical;
}
input:focus,textarea:focus,select:focus {
  outline:2px solid #7aa996;
  outline-offset:2px;
}
:focus-visible {
  outline:2px solid var(--accent);
  outline-offset:3px;
}
input[type=checkbox] {
  width:17px;
  height:17px;
  margin:0;
  accent-color:var(--accent);
}
.required {
  color:var(--accent);
}
fieldset {
  border:1px solid var(--line);
  border-radius:10px;
  padding:18px;
}
legend {
  font-size:13px;
  font-weight:650;
  padding:0 6px;
}
.checkbox-grid {
  display:grid;
  grid-template-columns:repeat(3,minmax(0,1fr));
  gap:12px;
  margin-bottom:14px;
}
.checkbox-grid label {
  display:flex;
  align-items:center;
  gap:9px;
  font-weight:400;
}
.reference-grid {
  display:grid;
  grid-template-columns:repeat(2,minmax(0,1fr));
  gap:24px;
}
.reference-panel {
  margin-bottom:0;
  scroll-margin-top:25px;
}
.compact-form {
  display:flex;
  gap:12px;
  align-items:flex-end;
}
.compact-form label {
  flex:1;
  min-width:0;
}
.reference-list {
  list-style:none;
  padding:0;
  margin:20px 0 0;
}
.reference-list li {
  display:flex;
  align-items:center;
  justify-content:space-between;
  border-top:1px solid var(--line);
  padding:12px 0;
  gap:12px;
}
.reference-list strong {
  font-size:14px;
  font-weight:550;
}
.reference-list small {
  display:inline-block;
  margin-left:12px;
}
.search-fields {
  display:flex;
  align-items:flex-end;
  gap:18px;
}
.search-fields label {
  flex:1;
}
.search-fields select {
  min-width:180px;
}
.notice {
  padding:16px 20px;
  margin-bottom:22px;
  border:1px solid var(--line);
  border-radius:10px;
}
.notice.success {
  color:#17543b;
  background:#eaf5ee;
  border-color:#bddec7;
}
.notice.warning,.validation-summary {
  color:#79551c;
  background:#fff6df;
  border-color:#ecdba6;
}
.notice.error {
  color:var(--danger);
  background:#fff0ee;
}
.validation-summary {
  padding:16px 20px 16px 38px;
  border:1px solid #ecdba6;
  border-radius:9px;
}
.empty-state {
  text-align:center;
  padding:40px;
  color:var(--muted);
}
.prose {
  max-width:960px;
}
.prose h2:not(:first-child) {
  margin-top:30px;
}
.prose li {
  margin:8px 0;
}
.error-panel {
  max-width:780px;
  margin:45px auto;
  padding:48px;
}
.site-footer {
  border-top:1px solid var(--line);
  color:var(--muted);
  font-size:12px;
  padding:25px 0;
}
.site-footer .container {
  display:flex;
  justify-content:space-between;
  gap:20px;
}
button:disabled,select:disabled {
  opacity:.55;
  cursor:not-allowed;
}
@media(max-width:1050px) {
  .nav-wrap {
    flex-direction:column;
    align-items:flex-start;
    padding:18px 0;
    gap:12px;
  }
  .hero {
    gap:30px;
  }
  .checkbox-grid {
    grid-template-columns:repeat(2,1fr);
  }
  .compact-form {
    flex-wrap:wrap;
  }
  .compact-form button {
    width:100%;
  }
  .search-fields {
    flex-wrap:wrap;
  }
}
@media(max-width:720px) {
  .container {
    width:92%;
  }
  main {
    padding-top:22px;
  }
  .hero {
    grid-template-columns:1fr;
    padding:14px 0 30px;
    gap:24px;
  }
  .hero-art {
    min-height:270px;
  }
  .film-card {
    height:230px;
    width:220px;
    padding:18px;
  }
  .film-card strong {
    font-size:22px;
  }
  .stats-grid {
    grid-template-columns:repeat(2,minmax(0,1fr));
  }
  .stat {
    padding:18px;
  }
  .stat strong {
    font-size:32px;
  }
  .feature-grid,.reference-grid,.form-grid {
    grid-template-columns:1fr;
  }
  .page-heading {
    align-items:flex-start;
    flex-direction:column;
  }
  .panel {
    padding:20px;
  }
  .checkbox-grid {
    grid-template-columns:1fr;
  }
  .search-fields label {
    flex-basis:100%;
  }
  .site-header nav {
    gap:2px;
  }
  .site-header nav a {
    padding:6px 8px;
    font-size:13px;
  }
  .site-footer .container {
    flex-direction:column;
    gap:8px;
  }
  .error-panel {
    padding:26px;
  }
  .hero h1 {
    font-size:43px;
  }
  .wide {
    grid-column:auto;
  }
}
@media(prefers-reduced-motion:reduce) {
  html {
    scroll-behavior:auto;
  }
}

/* Public portal: navigation, articles and tools. */
.nav-wrap { flex-wrap: wrap; align-items: center; padding-block: 1rem; }
.site-header nav { flex-wrap: wrap; gap: .6rem 1rem; align-items: center; }
.nav-dropdown { position: relative; }
.nav-dropdown summary { cursor: pointer; padding: .5rem 0; }
.dropdown-items { position: absolute; z-index: 20; top: 100%; left: 0; min-width: 250px; max-height: 70vh; overflow-y: auto; background: #fff; color: #172b36; border: 1px solid #d6dce0; border-radius: 12px; box-shadow: 0 12px 24px #172b3626; padding: .6rem; }
.site-header .dropdown-items a { display: block; color: #172b36; padding: .5rem .7rem; }
.site-header .dropdown-items a:hover { background: #edf4f3; }
.site-search-form { width: 100%; max-width: 480px; margin-left: auto; }
.site-search-form label { font-size: .85rem; margin-bottom: .25rem; }
.search-row { display: flex; gap: .5rem; align-items: center; }
.search-row input { min-width: 0; flex: 1; }
.search-row button { flex: none; }
.site-footer .footer-grid { display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; }
.site-footer p { margin: .5rem 0; }
.site-footer a { color: inherit; text-decoration: underline; overflow-wrap: anywhere; }
.catalog-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 1.5rem; }
.film-data { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 1rem; }
.film-data dt { font-weight: 600; }
.film-data dd { margin: .25rem 0 0; overflow-wrap: anywhere; }
.film-data ul { margin: 0; padding-left: 1.2rem; }
.full-width { grid-column: 1 / -1; }
.resource-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 1.5rem; }
.tool-form { max-width: 520px; display: grid; gap: .75rem; }
.tool-form button { justify-self: start; }
.result-box { margin-top: 1.5rem; padding: 1rem 1.25rem; border-left: 4px solid #267565; background: #edf5f1; border-radius: 8px; }
.live-preview { color: #49665d; }
.search-result { padding-block: 1rem; border-top: 1px solid #d6dce0; }
.prose dt { margin-top: .8rem; font-weight: 700; }
.prose dd { margin-left: 0; }
a:focus-visible, summary:focus-visible, button:focus-visible { outline: 3px solid #e4a644; outline-offset: 4px; }
@media (max-width: 900px) {
 .site-search-form { margin-left: 0; }
 .catalog-grid { grid-template-columns: 1fr; }
}
@media (max-width: 600px) {
 .site-footer .footer-grid, .resource-grid { grid-template-columns: 1fr; }
 .site-header nav { width: 100%; flex-direction: column; align-items: stretch; }
 .dropdown-items { position: static; min-width: 0; box-shadow: none; }
 .film-data { grid-template-columns: 1fr; }
}

```

### 8. JavaScript

#### [src/main/resources/static/js/main.js](src/main/resources/static/js/main.js)

```javascript
"use strict";
document.addEventListener("DOMContentLoaded", () => {
 document.querySelectorAll("form[data-confirm]").forEach(form => {
  form.addEventListener("submit", event => {
   if (!window.confirm(form.dataset.confirm)) event.preventDefault();
  });
 });
 // Focus the invalid form after server-side validation, without losing user input.
 const errors = document.querySelector(".validation-summary");
 if (errors) { errors.setAttribute("tabindex", "-1"); errors.focus(); }
 const path = window.location.pathname;
 document.querySelectorAll(".site-header nav a").forEach(link => {
  const href = new URL(link.href).pathname;
  if (href === path || (href === "/edit" && path.startsWith("/edit_film/"))) link.setAttribute("aria-current", "page");
 });

 const genre = document.getElementById("search-genre");
 const country = document.getElementById("search-country");
 const form = document.getElementById("search-form");
 const status = document.getElementById("search-status");
 const submit = document.getElementById("search-submit");
 if (!genre || !country || !form) return;
 let request;
 genre.addEventListener("change", async () => {
  if (request) request.abort();
  const current = new AbortController();
  request = current;
  country.replaceChildren(new Option("Все страны жанра", ""));
  country.disabled = true;
  status.textContent = "";
  submit.disabled = false;
  if (!genre.value) return;
  submit.disabled = true;
  status.textContent = "Загрузка стран…";
  try {
   const url = new URL(form.dataset.countriesUrl, window.location.origin);
   url.searchParams.set("genre", genre.value);
   const response = await fetch(url, { signal: current.signal, headers: { Accept: "application/json" } });
   if (!response.ok) throw new Error("Не удалось загрузить страны");
   const countries = await response.json();
   if (!Array.isArray(countries)) throw new Error("Неверный ответ сервера");
   if (request !== current) return;
   countries.forEach(item => country.add(new Option(item.title, item.id)));
   country.disabled = countries.length === 0;
   status.textContent = countries.length ? "Можно уточнить страну или искать по всему жанру." : "В этом жанре пока нет фильмов.";
  } catch (error) {
   if (error.name !== "AbortError" && request === current) status.textContent = "Не удалось загрузить страны. Нажмите «Найти», чтобы открыть фильмы жанра.";
  } finally {
   if (request === current) submit.disabled = false;
  }
 });

});

document.addEventListener("DOMContentLoaded", () => {
 document.querySelectorAll(".nav-dropdown").forEach(menu => {
  menu.addEventListener("toggle", () => {
   if (menu.open) document.querySelectorAll(".nav-dropdown").forEach(other => { if (other !== menu) other.open = false; });
  });
 });
 document.addEventListener("keydown", event => {
  if (event.key === "Escape") document.querySelectorAll(".nav-dropdown[open]").forEach(menu => { menu.open = false; menu.querySelector("summary").focus(); });
 });
 document.querySelectorAll("form[data-calculator]").forEach(form => {
  form.addEventListener("input", () => {
   const output = form.querySelector("output");
   const valid = [...form.querySelectorAll("input")].every(input => input.value !== "" && input.validity.valid);
   if (!valid) { output.textContent = ""; return; }
   const data = new FormData(form);
   if (form.dataset.calculator === "watch-time") {
    const minutes = Number(data.get("count")) * Number(data.get("duration"));
    output.textContent = "Предварительно: " + minutes + " мин, " + Math.floor(minutes / 60) + " ч " + minutes % 60 + " мин.";
   } else {
    const budget = Number(data.get("budget"));
    const boxOffice = Number(data.get("boxOffice"));
    output.textContent = "Предварительно: коэффициент " + (boxOffice / budget).toFixed(4) + ", разность " + (boxOffice - budget).toFixed(2) + ".";
   }
  });
 });
});

```

### 9. robots.txt

#### [src/main/resources/static/robots.txt](src/main/resources/static/robots.txt)

```text
User-agent: *
Allow: /
Disallow: /edit
Disallow: /site-search
Disallow: /get_countries
Disallow: /add_
Disallow: /update_
Disallow: /delete_

```

### 10. sitemap и SEO

#### [src/main/java/com/hellgardd/filmarchive/service/SeoService.java](src/main/java/com/hellgardd/filmarchive/service/SeoService.java)

```java
package com.hellgardd.filmarchive.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SeoService {
 private final String baseUrl;
 private final SiteSearchService pages;
 private final String robotsRules;
 public SeoService(@Value("${site.base-url}") String baseUrl, SiteSearchService pages) throws java.io.IOException {
  URI uri = URI.create(baseUrl);
  if (!("https".equals(uri.getScheme()) || "http".equals(uri.getScheme())) || uri.getHost() == null || uri.getRawQuery() != null || uri.getFragment() != null || uri.getUserInfo() != null || !(uri.getPath().isEmpty() || uri.getPath().equals("/")))
   throw new IllegalArgumentException("SITE_BASE_URL must be an HTTP(S) origin without credentials, query or path");
  this.baseUrl = baseUrl.replaceAll("/+$", ""); this.pages = pages;
  this.robotsRules = new org.springframework.core.io.ClassPathResource("static/robots.txt").getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
 }
 public String canonical(String path) { return baseUrl + path; }
 public String robots() {
  return robotsRules + "\nSitemap: " + canonical("/sitemap.xml") + "\n";
 }
 public String sitemap() {
  StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
  pages.pages().stream().filter(p -> !p.url().equals("/site-search")).forEach(p -> xml.append(" <url><loc>").append(escape(canonical(p.url()))).append("</loc></url>\n"));
  return xml.append("</urlset>\n").toString();
 }
 private String escape(String value) { return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;"); }
}

```

#### [src/main/java/com/hellgardd/filmarchive/controller/SeoController.java](src/main/java/com/hellgardd/filmarchive/controller/SeoController.java)

```java
package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.service.SeoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeoController {
 private final SeoService seo;
 public SeoController(SeoService seo) { this.seo = seo; }
 @GetMapping(value = "/robots.txt", produces = "text/plain;charset=UTF-8")
 public String robots() { return seo.robots(); }
 @GetMapping(value = "/sitemap.xml", produces = "application/xml;charset=UTF-8")
 public String sitemap() { return seo.sitemap(); }
}

```

### 11. application.properties

#### [src/main/resources/application.properties](src/main/resources/application.properties)

```properties
spring.application.name=film-archive
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/cinema_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=${SHOW_SQL:false}
spring.jpa.properties.hibernate.format_sql=true
spring.thymeleaf.encoding=UTF-8
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
server.servlet.encoding.force=true
server.error.include-message=never
server.error.include-stacktrace=never
server.error.include-binding-errors=never
app.seed-data=${SEED_DATA:true}
app.zone=Europe/Moscow

# Public origin used for canonical URLs, robots.txt and sitemap.xml.
site.base-url=${SITE_BASE_URL:http://localhost:8080}
site.contact.email=${SITE_CONTACT_EMAIL:sabadyshdv23@oiate.ru}
site.contact.telegram=${SITE_CONTACT_TELEGRAM:Hellgard}
# Public APIs used by the educational portal do not require keys.
currency.api.url=${CURRENCY_API_URL:https://api.frankfurter.dev}
weather.api.url=${WEATHER_API_URL:https://api.open-meteo.com}
weather.geocoding.url=${WEATHER_GEOCODING_URL:https://geocoding-api.open-meteo.com}

```

### 12. README

#### [README.md](README.md)

Текст документации находится по ссылке выше; структура, запуск, API, W3C и индексация описаны полностью.

### Дополнение: Maven и проверки

#### [pom.xml](pom.xml)

```html
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
         http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

 <modelVersion>4.0.0</modelVersion>

 <parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>3.5.13</version>
  <relativePath/>
 </parent>

 <groupId>com.hellgardd</groupId>
 <artifactId>film-archive</artifactId>
 <version>1.0.0</version>

 <name>Каталог фильмов</name>

 <properties>
  <java.version>21</java.version>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
 </properties>

 <dependencies>

  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>

  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>

  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>

  <dependency>
   <groupId>org.postgresql</groupId>
   <artifactId>postgresql</artifactId>
   <scope>runtime</scope>
  </dependency>

  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-test</artifactId>
   <scope>test</scope>
  </dependency>

  <dependency>
   <groupId>org.testcontainers</groupId>
   <artifactId>junit-jupiter</artifactId>
   <scope>test</scope>
  </dependency>
  <dependency>
   <groupId>org.testcontainers</groupId>
   <artifactId>postgresql</artifactId>
   <scope>test</scope>
  </dependency>
 </dependencies>

 <build>
  <plugins>
   <plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
   </plugin>
  </plugins>
 </build>

 <profiles>
  <profile>
   <id>integration</id>
   <build><plugins><plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <executions><execution><goals><goal>integration-test</goal><goal>verify</goal></goals></execution></executions>
   </plugin></plugins></build>
  </profile>
 </profiles>
</project>
```

#### [tools/check_contracts.py](tools/check_contracts.py)

```python
"""Static cross-layer checks; requires only Python 3 standard library."""
from pathlib import Path
from html.parser import HTMLParser
import re
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "src/main/java/com/hellgardd/filmarchive"
TEMPLATES = ROOT / "src/main/resources/templates"
errors = []


def check(condition, message):
    if not condition:
        errors.append(message)


def properties(class_name, package="dto"):
    source = (JAVA / package / (class_name + ".java")).read_text(encoding="utf-8")
    return {name[0].lower() + name[1:] for name in re.findall(r"public\s+[\w<>]+\s+get(\w+)\(", source)}


class TemplateParser(HTMLParser):
    VOID = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "param", "source", "track", "wbr"}

    def __init__(self, path):
        super().__init__(convert_charrefs=True)
        self.path = path
        self.stack = []
        self.forms = 0

    def handle_starttag(self, tag, attrs):
        if tag == "form":
            check(self.forms == 0, f"{self.path}: nested form")
            self.forms += 1
        if tag not in self.VOID:
            self.stack.append(tag)

    def handle_endtag(self, tag):
        if tag == "form":
            self.forms -= 1
        if tag not in self.VOID:
            check(bool(self.stack) and self.stack[-1] == tag, f"{self.path}: unbalanced </{tag}>")
            if self.stack:
                self.stack.pop()


form_properties = properties("FilmForm")
film_source = (JAVA / "entity/Film.java").read_text(encoding="utf-8")
film_properties = properties("Film", "entity")
for name in ("title", "description", "duration", "budget", "totalBoxOffice", "releaseDate", "genre", "country", "ageLimit", "shootingQuality", "actors"):
    check(name in film_properties, f"Missing Film property: {name}")
check(re.search(r'name\s*=\s*"film_people"', film_source), "Join table must be film_people")
check("CascadeType.REMOVE" not in film_source and "CascadeType.ALL" not in film_source, "Unsafe cascading removal")

form_types = {"filmForm": "FilmForm", "genreForm": "ReferenceForm", "countryForm": "ReferenceForm", "ageForm": "AgeLimitForm", "qualityForm": "QualityForm", "personForm": "PersonForm"}
controller = "\n".join(path.read_text(encoding="utf-8") for path in (JAVA / "controller").glob("*.java"))
routes = set(re.findall(r'@(Get|Post)Mapping\("([^"]+)"\)', controller))
required = {"Get": ["/", "/edit", "/edit_film/{id}", "/search", "/get_countries", "/aggregate", "/info"],
            "Post": ["/add_genre", "/add_country", "/add_quality", "/add_age_limit", "/add_person", "/delete_person/{id}", "/add_film", "/update_film/{id}", "/delete_film/{id}"]}
required["Get"] += ["/catalog", "/site-search", "/services", "/about", "/history", "/genres-info", "/cinema-technologies", "/ratings-info", "/formats", "/recommendations", "/links", "/contacts", "/services/random-film", "/services/watch-time", "/services/profitability", "/services/age-check", "/services/currency", "/services/weather"]
for method, paths in required.items():
    for path in paths:
        check((method, path) in routes, f"Missing {method} route {path}")
for path in TEMPLATES.rglob("*.html"):
    html = path.read_text(encoding="utf-8")
    parser = TemplateParser(path.relative_to(ROOT))
    parser.feed(html)
    check(not parser.stack, f"{path.name}: unclosed tags {parser.stack}")
    check('lang="ru"' in html, f"{path.name}: missing Russian language")
    for fragment, name in re.findall(r"~\{([\w/-]+)\s*::\s*(\w+)", html):
        target = TEMPLATES / (fragment + ".html")
        check(target.is_file(), f"{path.name}: missing fragment file {fragment}")
        if target.is_file():
            check(re.search(r'th:fragment="' + name + r'(?:\(|")', target.read_text(encoding="utf-8")), f"Missing fragment {fragment}::{name}")
    for form, inner in re.findall(r'<form[^>]+th:object="\$\{(\w+)\}"[^>]*>(.*?)</form>', html, re.S):
        check(form in form_types, f"{path.name}: unknown model form {form}")
        known = properties(form_types[form]) if form in form_types else set()
        for field in re.findall(r'th:field="\*\{(\w+)\}"', inner):
            check(field in known, f"{path.name}: {form}.{field} is not a DTO property")
    if path.name == "film-fields.html":
        for field in re.findall(r'th:field="\*\{(\w+)\}"', html):
            check(field in form_properties, f"Shared film form field {field} is not in FilmForm")
    for prop in re.findall(r"\$\{f\.(\w+)", html):
        check(prop in film_properties, f"{path.name}: unknown Film property {prop}")

ns = {"m": "http://maven.apache.org/POM/4.0.0"}
pom = ET.parse(ROOT / "pom.xml")
artifacts = {n.text for n in pom.findall(".//m:dependency/m:artifactId", ns)}
for dependency in ("spring-boot-starter-web", "spring-boot-starter-data-jpa", "spring-boot-starter-thymeleaf", "spring-boot-starter-validation", "postgresql"):
    check(dependency in artifacts, f"Missing Maven dependency {dependency}")
check(pom.findtext(".//m:java.version", namespaces=ns) == "21", "Java version must be 21")
check(not any("mysql" in a.lower() or "ucanaccess" in a.lower() for a in artifacts), "Forbidden database dependency")
for name in ("Film", "Genre", "Country", "AgeLimit", "ShootingQuality", "Person"):
    repository = (JAVA / "repository" / (name + "Repository.java")).read_text(encoding="utf-8")
    check(re.search(rf"JpaRepository\s*<\s*{name}\s*,\s*Long\s*>", repository), f"{name}Repository must extend JpaRepository")

if errors:
    raise SystemExit("\n".join(errors))
print("OK: DTO fields, Film properties, routes, fragments, HTML structure, repositories and Maven dependencies agree.")

```

#### [tools/check_rendered_html.py](tools/check_rendered_html.py)

```python
"""Validate rendered HTML structure locally; complements the W3C Nu validator."""
from html.parser import HTMLParser
from pathlib import Path
import sys

VOID = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "source", "track", "wbr"}

class Page(HTMLParser):
    def __init__(self):
        super().__init__()
        self.stack, self.ids, self.labels, self.controls, self.errors = [], set(), [], [], []
        self.title_count = self.main_count = self.header_count = self.footer_count = 0
        self.has_charset = self.has_viewport = self.has_description = self.has_doctype = False
        self.language = None
        self.forms = 0

    def handle_decl(self, decl):
        self.has_doctype |= decl.lower() == "doctype html"

    def handle_starttag(self, tag, attributes):
        attrs = dict(attributes)
        if any(name.startswith("th:") for name in attrs):
            self.errors.append("unprocessed Thymeleaf attribute")
        if "id" in attrs:
            if attrs["id"] in self.ids:
                self.errors.append("duplicate id: " + attrs["id"])
            self.ids.add(attrs["id"])
        if tag == "html": self.language = attrs.get("lang")
        if tag == "title": self.title_count += 1
        if tag == "main": self.main_count += 1
        if tag == "header": self.header_count += 1
        if tag == "footer": self.footer_count += 1
        if tag == "meta":
            self.has_charset |= attrs.get("charset", "").upper() == "UTF-8"
            self.has_viewport |= attrs.get("name") == "viewport"
            self.has_description |= attrs.get("name") == "description" and bool(attrs.get("content"))
        if tag == "img" and "alt" not in attrs: self.errors.append("img without alt")
        if tag == "button" and attrs.get("type") not in {"submit", "button", "reset"}:
            self.errors.append("button without correct type")
        if tag == "label" and "for" in attrs: self.labels.append(attrs["for"])
        if tag in {"input", "select", "textarea"} and attrs.get("type") not in {"hidden", "submit", "button", "reset"}:
            self.controls.append((attrs.get("id"), "label" in self.stack))
        if tag == "a" and attrs.get("target") == "_blank" and not {"noopener", "noreferrer"}.issubset(set(attrs.get("rel", "").split())):
            self.errors.append("external link without safe rel")
        if tag == "form":
            if self.forms: self.errors.append("nested form")
            self.forms += 1
        if tag not in VOID: self.stack.append(tag)

    def handle_endtag(self, tag):
        if tag == "form": self.forms -= 1
        if tag in VOID: return
        if not self.stack or self.stack[-1] != tag:
            self.errors.append("unbalanced closing tag: " + tag)
        else:
            self.stack.pop()

    def validate(self):
        if self.stack: self.errors.append("unclosed tags: " + str(self.stack))
        if self.language != "ru": self.errors.append("missing lang=ru")
        if not self.has_doctype: self.errors.append("missing HTML5 doctype")
        if self.title_count != 1: self.errors.append("expected one title")
        if (self.main_count, self.header_count, self.footer_count) != (1, 1, 1):
            self.errors.append("expected one header, main and footer")
        if not all((self.has_charset, self.has_viewport, self.has_description)):
            self.errors.append("missing head metadata")
        for target in self.labels:
            if target not in self.ids: self.errors.append("label points to missing id: " + target)
        for id_, wrapped in self.controls:
            if not wrapped and id_ not in self.labels: self.errors.append("control without label: " + str(id_))
        return self.errors

folder = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(__file__).resolve().parents[1] / "target/rendered-html"
files = sorted(folder.glob("*.html"))
if not files: raise SystemExit("No rendered HTML files. Run mvn test first.")
errors = []
for file in files:
    page = Page()
    page.feed(file.read_text(encoding="utf-8"))
    errors.extend(str(file.name) + ": " + error for error in page.validate())
if errors: raise SystemExit("\n".join(errors))
print(f"OK: {len(files)} rendered pages: nesting, ids, labels, metadata, fragments and external links.")

```

#### [src/test/java/com/hellgardd/filmarchive/PortalServiceTest.java](src/test/java/com/hellgardd/filmarchive/PortalServiceTest.java)

```java
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

```

#### [src/test/java/com/hellgardd/filmarchive/ExternalServicesTest.java](src/test/java/com/hellgardd/filmarchive/ExternalServicesTest.java)

```java
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

```

#### [src/test/java/com/hellgardd/filmarchive/PortalWebTest.java](src/test/java/com/hellgardd/filmarchive/PortalWebTest.java)

```java
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

```

## Чек-лист

# Соответствие требованиям лабораторной работы

| Требование | Статус и реализация |
|---|---|
| Минимум 10 статических страниц | Выполнено: index, about, history, genres-info, cinema-technologies, ratings-info, formats, recommendations, links, contacts в src/main/resources/templates; у каждой отдельный GET URL. |
| Минимум 10 сервисов | Выполнено: каталог, поиск фильмов, поиск сайта, статистика, случайный фильм, время просмотра, окупаемость, проверка возраста, валюта, погода. Карточки — /services. |
| Единое меню на каждой странице | Выполнено: fragments/header.html :: navigation; выпадающие информационные страницы и сервисы; сохранённые административные страницы и ошибки тоже используют fragment. |
| Поиск на каждой странице | Выполнено: форма query в header, GET /site-search. Это отдельный поиск страниц, а не поиск фильмов в PostgreSQL. |
| Общий footer | Выполнено: fragments/footer.html :: footer на всех страницах. |
| Назначение сайта | Выполнено: главная, /about, /contacts и footer. |
| Разработчик и контакты | Выполнено: Даниил Сабадыш, sabadyshdv23@oiate.ru, @Hellgard — в footer и /contacts. |
| Название проекта и год | Выполнено: FilmArchive, © 2026 FilmArchive в footer. |
| Внешние тематические ссылки | Выполнено: 8 ресурсов на /links, понятные тексты, target=_blank, rel=noopener noreferrer. |
| Прикладной сервис с БД | Выполнено: каталог /catalog и существующее управление /edit; PostgreSQL, FilmService и JPA repository. |
| Существующие сущности и связи | Сохранены: Film, Genre, Country, AgeLimit, ShootingQuality, Person и Film ↔ Person. |
| Архитектура Spring Boot | Сохранена: controller/service/repository/entity/dto/exception/config; бизнес-логика вне контроллеров. |
| Погода и валюта | Выполнено: отдельные WeatherService и CurrencyService, API URL из properties/окружения, таймауты, DTO результатов. Выбранные API не требуют ключей. |
| Понятные ошибки | Выполнено: GlobalExceptionHandler; 404 для отсутствующих страниц, 400 для неверных данных, 503 для БД/API; без stack trace в HTML. |
| W3C | Подготовлено: семантический HTML5, lang, charset, viewport, title, description, labels, типы кнопок, уникальные id. Проверены 25 отрендеренных страниц локальным HTML-скриптом. Внешняя W3C-проверка не выполнялась: загрузите готовый HTML из target/rendered-html в Nu HTML Checker или проверьте публичный URL. |
| robots.txt | Выполнено: static/robots.txt хранит правила; GET /robots.txt добавляет Sitemap с настроенным доменом через SeoService/SeoController. |
| sitemap.xml | Выполнено: GET /sitemap.xml генерируется из публичного реестра SiteSearchService, без административных страниц и результатов поиска сайта. |
| Подготовка к индексации | Выполнено: title, description, canonical, noindex для управления/результатов, robots и sitemap. После публикации задайте SITE_BASE_URL, включите HTTPS, проверьте доступность страниц и передайте sitemap поисковикам. Localhost не индексируется. |
| README | Выполнено: назначение, стек, структура, БД, запуск, URL, сервисы, поиск, проверки и публикация. |

## Десять сервисов и URL

1. Каталог — /catalog.
2. Поиск фильмов — /search.
3. Поиск по сайту — /site-search.
4. Статистика — /aggregate.
5. Случайный фильм — /services/random-film.
6. Время просмотра — /services/watch-time.
7. Окупаемость — /services/profitability.
8. Возраст — /services/age-check.
9. Валюта — /services/currency.
10. Погода — /services/weather.

## Что ещё проверить перед сдачей и публикацией

- Выполнить mvn clean verify на JDK 21 с доступом к Maven Central.
- Запустить PostgreSQL и проверить приложение со своими DB_URL/DB_USERNAME/DB_PASSWORD.
- Выполнить mvn -Pintegration verify при запущенном Docker для существующего полного CRUD-сценария.
- Проверить готовые страницы W3C Nu HTML Checker, включая заполненные формы и результаты.
- После публикации указать реальный SITE_BASE_URL и проверить /robots.txt, /sitemap.xml и canonical.
- Ограничить доступ к административным операциям перед публичным размещением: robots регулирует обход, а не доступ.
