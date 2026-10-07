package com.hellgardd.filmarchive.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.*;
import com.hellgardd.filmarchive.service.DatabaseInitializationService;
@Configuration
public class DataInitializer {
 @Bean public ApplicationRunner initializeDatabase(DatabaseInitializationService initializer) { return args -> initializer.initialize(); }
}
