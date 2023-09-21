package com.celuveat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@ConfigurationPropertiesScan
public class CeluveatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CeluveatApplication.class, args);
    }
}
