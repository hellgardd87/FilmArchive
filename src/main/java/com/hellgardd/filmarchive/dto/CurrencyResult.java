package com.hellgardd.filmarchive.dto;

public record CurrencyResult(java.math.BigDecimal amount, String from, String to, java.math.BigDecimal rate, java.math.BigDecimal converted, java.time.LocalDate date) {}
