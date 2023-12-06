package pl.edu.pg.eti.dragondestiny.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Gateway Application for connecting the microservices.
 */

@SpringBootApplication
public class Gateway {

    public static void main(String[] args) {

        SpringApplication.run(Gateway.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes() 
                .route("websocket", r -> r
                        .path("/playedgame/updates/**")
                        .uri("lb:ws://played-game"))
                .route("users", r -> r
                        .path("/api/users/**")
                        .uri("lb://game-user"))
                .route("playedgames", r -> r
                        .path("/api/playedgames/**")
                        .uri("lb://played-game"))
                .route("games", r -> r
                        .path(
                                "/api/games/**", // game controller + game card controller + game character controller + game field controller
                                "/api/boards/**", // board controller
                                "/api/fields/**", // field controller
                                "/api/cards/**",  // card controller
                                "/api/characters/**" // characters controller
                        )
                        .uri("lb://game-engine"))
                .route("graphics", r -> r
                        .path("/api/graphics/**")
                        .uri("lb://game-graphics"))
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
