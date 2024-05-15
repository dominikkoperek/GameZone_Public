package com.example.gamezoneproject;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

@SpringBootApplication
public class GameZoneProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameZoneProjectApplication.class, args);

    }

    @Bean
    public SecureRandom getRandom() {
        return new SecureRandom();
    }
}
