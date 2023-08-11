package pl.edu.pg.eti.dragondestiny.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server will maintain all configuration of the microservices.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServer {

    public static void main(String[] args) {

        SpringApplication.run(ConfigServer.class, args);
    }

}