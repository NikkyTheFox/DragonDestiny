package com.example.game_engine.board.dto;

import com.example.game_engine.field.dto.FieldDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class BoardDTO
{
    /**
     * Unique identifier of the board.
     */
    private Integer id;

    /**
     * Size of board (number of fields) in x dimension.
     */
    private Integer xSize;

    /**
     * Size of board (number of fields) in y dimension.
     */
    private Integer ySize;

    /**
     * List of fields in the board.
     */
    private List<FieldDTO> fieldsInBoard = new ArrayList<FieldDTO>();
}
