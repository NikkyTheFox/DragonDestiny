package pl.edu.pg.eti.dragondestiny.playedgame.field.DTO;

import lombok.Data;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldType;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class FieldDTO {

    /**
     * Identifier of the field.
     */
    private Integer id;

    /**
     * Type of field - describes the function of the field.
     */
    private FieldType type;

    /**
     * xPosition is equal to 0 (first row) or Board.ySize (last row).
     */
    private Integer xPosition = 0;

    /**
     * yPosition is equal to 0 (first column) or Board.xSize (last column).
     */
    private Integer yPosition = 0;

    /**
     * Enemy Card corresponding to enemy on that field.
     */
    private EnemyCardListDTO enemyCardList;
}
