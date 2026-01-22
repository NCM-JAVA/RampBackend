package com.ramp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ramp.repo")
@EntityScan(basePackages = "com.ramp.entity")
public class RampApplication {

    public static void main(String[] args) {
        SpringApplication.run(RampApplication.class, args);
    }
}
