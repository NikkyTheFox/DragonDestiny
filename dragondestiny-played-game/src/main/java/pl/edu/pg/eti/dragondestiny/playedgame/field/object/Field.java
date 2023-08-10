package pl.edu.pg.eti.dragondestiny.playedgame.field.object;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;

import java.io.Serializable;

/**
 * Corresponds to fields on played board.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Field implements Serializable {

    /**
     * Identifier of the field.
     */
    @Id
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
    private EnemyCard enemy;
}
