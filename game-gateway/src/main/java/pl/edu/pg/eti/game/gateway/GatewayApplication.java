package pl.edu.pg.eti.game.gateway;

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

/**
 * Gateway Application for connecting the microservices.
 */

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
                .route("users", r -> r
                        .path("/api/users/{login}", "/api/users/{login}/edit", "/api/users/{login}/games", "/api/users/{login}/addGame/{gameId}",
                                "/api/users", "/api/users/login", "/api/users/register")
                        .uri("lb://game-user"))
                .route("playedgames", r -> r
                        .path("/api/playedgames",
                                "/api/playedgames/{playedGameId}",
                                "/api/playedgames/{playedGameId}/cards/deck",
                                "/api/playedgames/{playedGameId}/cards/deck/{cardId}",
                                "/api/playedgames/{playedGameId}/cards/used",
                                "/api/playedgames/{playedGameId}/cards/used/{cardId}",
                                "/api/playedgames/{playedGameId}/cardToUsed/{cardId}",
                                "/api/playedgames/{playedGameId}/players/{playerId}/cardToPlayer/{cardId}",
                                "/api/playedgames/{playedGameId}/characters",
                                "/api/playedgames/{playedGameId}/characters/{characterId}",
                                "/api/playedgames/{playedGameId}/players",
                                "/api/playedgames/{playedGameId}/players/{playerId}",
                                "/api/playedgames/{playedGameId}/players/{playerId}/cards",
                                "/api/playedgames/{playedGameId}/players/{playerId}/cards/{cardId}",
                                "/api/playedgames/{playedGameId}/addPlayer", "/api/playedgames/{playedGameId}/addPlayer/{playerLogin}",
                                "/api/playedgames/{playedGameId}/players/{playerId}/character/{characterId}",
                                "/api/playedgames/{playedGameId}/players/{playerId}/character/{characterId}/field/{fieldId}")
                        .uri("lb://played-game"))
                .route("games", r -> r
                        .path(
                                // GameController
                                "/api/games", "/api/games/{id}", "/api/games/{id}/board",
                                // BoardController
                                "/api/boards", "/api/boards/{id}",
                                // FieldController
                                "/api/fields", "/api/fields/{id}",
                                // BoardFieldController
                                "/api/boards/{boardId}/fields", "/api/boards/{boardId}/fields/{id}",
                                // GameFieldController
                                "/api/games/{gameId}/board/fields", "/api/games/{gameId}/board/fields/{id}",
                                // GameCardController:
                                "/api/games/{gameId}/cards", "/api/games/{gameId}/cards/{id}",
                                "/api/games/{gameId}/cards/enemy", "/api/games/{gameId}/cards/item",
                                // CardController:
                                "/api/cards", "/api/cards/{id}", "/api/cards/enemy", "/api/cards/item",
                                // CharacterController:
                                "/api/characters", "/api/characters/{id}",
                                // GameCharacterController:
                                "/api/games/{gameId}/characters", "/api/games/{gameId}/characters/{id}"
                            )
                        .uri("lb://game-engine"))
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
