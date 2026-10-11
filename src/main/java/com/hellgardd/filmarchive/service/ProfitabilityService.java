package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.ProfitabilityResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class ProfitabilityService {
 public ProfitabilityResult calculate(BigDecimal budget, BigDecimal boxOffice) {
  validate(budget, false);
  validate(boxOffice, true);
  return new ProfitabilityResult(boxOffice.divide(budget, 4, RoundingMode.HALF_UP), boxOffice.subtract(budget));
 }
 private void validate(BigDecimal value, boolean allowZero) {
  if (value == null || value.signum() < 0 || (!allowZero && value.signum() == 0) || value.compareTo(new BigDecimal("1000000000000")) > 0 || value.scale() > 2)
   throw new IllegalArgumentException("Бюджет должен быть положительным, сборы — неотрицательными; максимум 1 трлн, два знака после запятой");
 }
}
