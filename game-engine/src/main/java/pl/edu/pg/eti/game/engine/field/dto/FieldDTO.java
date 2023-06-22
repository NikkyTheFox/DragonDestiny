package pl.edu.pg.eti.game.engine.field.dto;

import jakarta.persistence.OneToOne;
import pl.edu.pg.eti.game.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.game.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.engine.field.entity.FieldType;
import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 */

@Data
public class FieldDTO {

    /**
     * Unique identifier of field.
     */
    private Integer id;

    /**
     * Type of field - describes the function of the field.
     */
    private FieldType type;

    /**
     * xPosition is between to 0 (first row) and Board.ySize (last row).
     * Represents location of field on the board.
     */
    private Integer xPosition;

    /**
     * yPosition is between to 0 (first column) and Board.xSize (last column).
     * Represents location of field on the board.
     */
    private Integer yPosition;

    /**
     * Enemy Card corresponding to enemy on that field.
     */
    private EnemyCardDTO enemy;

}
