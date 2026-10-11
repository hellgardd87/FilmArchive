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
