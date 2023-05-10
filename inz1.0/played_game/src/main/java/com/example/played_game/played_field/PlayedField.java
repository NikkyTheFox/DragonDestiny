package com.example.played_game.played_field;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Corresponds to fields on played board.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
public class PlayedField implements Serializable
{
    /**
     * Identifier of the field.
     */
    @Id
    private Integer id;
    /**
     * Type of field - describes the function of the field.
     */
    private FieldTypesEnum fieldType;
    /**
     * xPosition is equal to 0 (first row) or Board.ySize (last row).
     */
    private Integer xPosition;
    /**
     * yPosition is equal to 0 (first column) or Board.xSize (last column).
     */
    private Integer yPosition;

}
