package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder)
    {
        return builder
                .routes()
                .route("playedCharacters", r -> r
                        .path("/api/playedCharacters/{characterId}", "/api/playedCharacters")
                        .uri("lb://microservice-played-character"))
                .route("players", r -> r
                        .path("/api/players", "/api/players/{playerId}")
                        .uri("lb://microservice-player"))
                .route("games", r -> r
                        .path(
                                // GameController
                                "/api/games", "/api/games/{id}", "/api/games/{id}/board", "/api/games/{gameid}/board/{id}",
                                // BoardController
                                "/api/boards", "/api/boards/{id}",
                                // FieldController
                                "/api/fields", "/api/fields/{id}",
                                // BoardFieldController
                                "/api/boards/{boardid}/fields", "/api/boards/{boardid}/fields/{id}",
                                "/api/games/{gameid}/board/{boardid}/fields", "/api/games/{gameid}/board/{boardid}/fields/{id}",
                                // GameCardController:
                                "/api/games/{gameid}/cards", "/api/games/{gameid}/cards/{id}",
                                "/api/games/{gameid}/cards/enemycards", "/api/games/{gameid}/cards/itemcards",
                                // CardController:
                                "/api/cards", "/api/cards/{id}", "/api/cards/enemycards", "/api/cards/itemcards",
                                // CharacterController:
                                "/api/characters", "/api/characters/{id}",
                                // GameCharacterController:
                                "/api/games/{gameid}/characters", "/api/games/{gameid}/characters/{id}"
                            )
                        .uri("lb://microservice-game"))
                .build();
    }
    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }


}
