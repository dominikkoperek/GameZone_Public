package com.example.gamezoneproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.SecureRandom;

@SpringBootApplication
public class GameZoneProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameZoneProjectApplication.class, args);

    }
    @Bean
    public SecureRandom getRandom(){
        return  new SecureRandom();
    }
}
