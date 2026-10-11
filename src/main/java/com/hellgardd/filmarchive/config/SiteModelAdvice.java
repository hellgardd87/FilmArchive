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
