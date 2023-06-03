package pl.edu.pg.eti.game.engine.field.entity;

import pl.edu.pg.eti.game.engine.board.entity.Board;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.EqualsAndHashCode;

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

}
