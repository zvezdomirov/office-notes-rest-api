package com.officenotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OfficenotesApplication {

    public static void main(String[] args) {
        LoadDatabase loadDatabase = new LoadDatabase();
        SpringApplication.run(OfficenotesApplication.class, args);
    }

}