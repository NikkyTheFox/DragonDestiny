package pl.edu.pg.eti.dragondestiny.engine.board.entity;


import jakarta.persistence.*;
import lombok.*;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a whole board that is available to play.
 * Size of the board is xSize * 2 + ySize * 2 -> a rectangular frame.
 * Size represents number of fields.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Table(name = "boards")
public class Board {

    /**
     * Identifier of a board.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer id;

    /**
     * Number of fields in x-dimension.
     */
    @Column(name = "x_size")
    private Integer xsize;

    /**
     * Number of fields in y-dimension.
     */
    @Column(name = "y_size")
    private Integer ysize;

    /**
     * List of fields in the board.
     */
    @OneToMany(mappedBy = "board")
    private List<Field> fields = new ArrayList<>();

    /**
     * List of games the board belongs to.
     * One board can belong to many games (game boxes).
     */
    @OneToMany(mappedBy = "board")
    private List<Game> games = new ArrayList<>();

}
