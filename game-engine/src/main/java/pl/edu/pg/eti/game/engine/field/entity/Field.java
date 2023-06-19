package pl.edu.pg.eti.game.engine.field.entity;

import jakarta.persistence.*;
import pl.edu.pg.eti.game.engine.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import pl.edu.pg.eti.game.engine.card.enemycard.entity.EnemyCard;

/**
 * Represent a field (of possible board) in the game.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Table(name = "fields")
public class Field {

    /**
     * Unique identifier of field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Integer id;

    /**
     * Type of field - describes the function of the field.
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private FieldType type;

    /**
     * xPosition is equal to 0 (first row) or Board.ySize (last row).
     */
    @Column(name = "x_position")
    private Integer xPosition;

    /**
     * yPosition is equal to 0 (first column) or Board.xSize (last column).
     */
    @Column(name = "y_position")
    private Integer yPosition;

    /**
     * Board the field belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    /**
     * Enemy Card corresponding to enemy on that field.
     */
    @OneToOne
    private EnemyCard enemy;

}
