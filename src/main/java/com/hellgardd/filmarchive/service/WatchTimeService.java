package com.hellgardd.filmarchive.service;

import com.hellgardd.filmarchive.dto.WatchTimeResult;
import org.springframework.stereotype.Service;

@Service
public class WatchTimeService {
 public WatchTimeResult calculate(int count, int averageMinutes) {
  if (count < 1 || count > 10000 || averageMinutes < 1 || averageMinutes > 1440)
   throw new IllegalArgumentException("Количество: 1–10000; длительность: 1–1440 минут");
  long total = (long) count * averageMinutes;
  return new WatchTimeResult(total, total / 60, total % 60);
 }
}
