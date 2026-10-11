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
