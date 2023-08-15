package pl.edu.pg.eti.dragondestiny.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Import(UserConfig.class)
@OpenAPIDefinition(info = @Info(title = "Dragon Destiny User API", version = "v1", description =
        "Used to retrieve all information about users and games they play."))

public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
