package pl.edu.pg.eti.dragondestiny.playedgame.initialization.webconfig;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating a load balanced WebClient instance.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a LoadBalanced WebClient.Builder bean.
     *
     * @return A LoadBalanced WebClient.Builder instance.
     */
    @LoadBalanced
    @Bean
    public WebClient.Builder loadBalancedWebClientBuilder() {

        return WebClient.builder();
    }
}