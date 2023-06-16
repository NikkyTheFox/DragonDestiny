package pl.edu.pg.eti.game.user.game;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GameDTO {
    @Id
    private String id;

}
