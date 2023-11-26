package pl.edu.pg.eti.dragondestiny.playedgame.field.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldOptionDTO {
    public FieldOptionEnum fieldOptionEnum;

    public Integer numOfCardsToTake;

    public Integer numOfTurnsToBlock;

    public Player enemyPlayer;
}
