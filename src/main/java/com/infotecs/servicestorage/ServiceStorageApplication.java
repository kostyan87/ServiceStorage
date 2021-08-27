package com.infotecs.servicestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServiceStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceStorageApplication.class, args);
    }

}
