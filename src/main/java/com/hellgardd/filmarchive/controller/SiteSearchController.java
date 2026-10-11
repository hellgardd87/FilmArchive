package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.service.SiteSearchService;
import com.hellgardd.filmarchive.dto.SitePage;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteSearchController {
 private final SiteSearchService search;
 public SiteSearchController(SiteSearchService search) { this.search = search; }
 @GetMapping(value = "/site-search", params = "suggest=true", produces = "application/json")
 @ResponseBody
 public List<SitePage> suggestions(@RequestParam(defaultValue = "") String query) {
  return search.search(query).stream().limit(8).toList();
 }
 @GetMapping("/site-search")
 public String search(@RequestParam(defaultValue = "") String query, Model model) {
  model.addAttribute("query", query);
  model.addAttribute("results", search.search(query));
  return "site-search";
 }
}
