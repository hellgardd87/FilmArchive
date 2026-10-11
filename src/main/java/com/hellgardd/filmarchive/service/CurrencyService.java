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
