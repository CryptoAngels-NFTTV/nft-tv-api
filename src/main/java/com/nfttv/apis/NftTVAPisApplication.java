package com.nfttv.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableMongoRepositories
@EnableWebMvc
@EnableCaching
@EnableWebSecurity
public class NftTVAPisApplication {

    public static void main(String[] args) {
        SpringApplication.run(NftTVAPisApplication.class, args);
    }



}
