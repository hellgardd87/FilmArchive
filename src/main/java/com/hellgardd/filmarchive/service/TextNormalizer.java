package com.hellgardd.filmarchive.service;

import java.text.Normalizer;
import org.springframework.stereotype.Component;

@Component
public class TextNormalizer {
 public String normalize(String value) { return value==null ? "" : Normalizer.normalize(value,Normalizer.Form.NFKC).strip().replaceAll("\\s+"," "); }
}
