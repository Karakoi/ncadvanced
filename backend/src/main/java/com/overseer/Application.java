package com.overseer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point.
 */
@SpringBootApplication
public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
