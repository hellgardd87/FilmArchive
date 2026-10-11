package com.hellgardd.filmarchive.service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgeCheckService {
 public List<Integer> ratings() { return List.of(0, 6, 12, 16, 18); }
 public boolean check(int age, int rating) {
  if (age < 0 || age > 120 || !ratings().contains(rating))
   throw new IllegalArgumentException("Укажите возраст от 0 до 120 и рейтинг из списка");
  return age >= rating;
 }
}
