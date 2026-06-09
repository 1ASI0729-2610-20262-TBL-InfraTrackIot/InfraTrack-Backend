package com.techtitans.infratrack.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InfraTrackWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfraTrackWebServiceApplication.class, args);
    }

}
