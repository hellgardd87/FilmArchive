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
