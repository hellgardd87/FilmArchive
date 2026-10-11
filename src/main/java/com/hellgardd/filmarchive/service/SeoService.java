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
