package com.hellgardd.filmarchive.dto;

public record WeatherResult(String city, String country, double temperature, double feelsLike, String description, String observedAt) {}
