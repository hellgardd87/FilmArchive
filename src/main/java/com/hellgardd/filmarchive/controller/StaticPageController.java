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
