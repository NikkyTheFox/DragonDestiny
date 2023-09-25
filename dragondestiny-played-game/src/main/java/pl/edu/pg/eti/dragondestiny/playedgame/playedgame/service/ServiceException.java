package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public class ServiceException extends Exception {

    private HttpStatusCode statusCode;

    private String message;

    public String returnMessage() {
        return "{\"Error\": \"" + message + "\"}";
    }
}
