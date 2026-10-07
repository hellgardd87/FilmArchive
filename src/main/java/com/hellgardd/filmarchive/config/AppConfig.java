package com.hellgardd.filmarchive.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {
 @Bean public Clock clock(@Value("${app.zone:Europe/Moscow}") String zone) { return Clock.system(ZoneId.of(zone)); }
}
