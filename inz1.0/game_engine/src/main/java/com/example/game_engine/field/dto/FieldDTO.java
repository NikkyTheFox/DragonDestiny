package com.example.game_engine.field.dto;

import com.example.game_engine.field.entity.FieldTypesEnum;
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
    private FieldTypesEnum fieldType;

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

}
