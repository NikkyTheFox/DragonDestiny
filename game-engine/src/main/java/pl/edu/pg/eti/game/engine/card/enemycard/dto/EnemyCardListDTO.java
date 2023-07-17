package pl.edu.pg.eti.game.engine.card.enemycard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EnemyCardListDTO {

    private List<EnemyCardDTO> enemyCardList;

}
