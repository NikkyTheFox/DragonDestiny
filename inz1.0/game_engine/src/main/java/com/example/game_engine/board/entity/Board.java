package com.example.game_engine.board.entity;


import com.example.game_engine.field.entity.Field;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder
@ToString
@EqualsAndHashCode
@Table(name = "boards")
public class Board
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer id;
    /**
     * Number of fields in x-dimension.
     */
    @Column(name = "x_size")
    private Integer xSize;
    /**
     * Number of fields in u-dimension.
     */
    @Column(name = "y_size")
    private Integer ySize;
    /**
     * List of fields in the board - one board => many fields.
     */
    @OneToMany(mappedBy = "id")
    private List<Field> fieldsInBoard = new ArrayList<Field>();

}
