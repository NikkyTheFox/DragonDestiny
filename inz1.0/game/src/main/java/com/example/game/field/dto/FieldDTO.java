package com.example.game.field.dto;

import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.field.entity.FieldTypesEnum;
import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 *
 */
@Data
public class FieldDTO {
    private Integer id;
    private FieldTypesEnum fieldType;
    private Integer xPosition;
    private Integer yPosition;
    private Integer boardId;
}
