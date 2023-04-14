package com.example.player;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PlayerApplication {

    @Value("${microservice-player.value}")
    String welcomeText;
    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);
    }

    @RequestMapping(value = "/")
    public String welcomeText() {
        return welcomeText;
    }
}
