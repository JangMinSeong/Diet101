package com.d101.back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackApplication {

    private static final Logger log = LoggerFactory.getLogger(BackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
        log.info("JWT_SECRET: {}", System.getenv("JWT_SECRET"));
        log.info("DATABASE_PASSWORD: {}", System.getenv("DATABASE_PASSWORD"));
        log.info("DATABASE_USERNAME: {}", System.getenv("DATABASE_USERNAME"));
    }

}
