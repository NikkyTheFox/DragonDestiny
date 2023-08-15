package pl.edu.pg.eti.dragondestiny.playedgame;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dragon Destiny Played Game API", version = "v1", description =
		"Used to retrieve all information about played games by players"))
public class PlayedGameApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlayedGameApplication.class, args);
	}
}
