package com.woowahan.woowahanbatchservice;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class WoowahanBatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WoowahanBatchServiceApplication.class, args);
    }

}
