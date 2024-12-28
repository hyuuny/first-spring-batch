package com.hyuuny.firstspringbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FirstSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringBatchApplication.class, args);
    }

}
