package com.example.game.board.dto;

import com.example.game.field.dto.FieldDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DTO allows to hide implementation from the client.
 *
 */
@Data
public class BoardDTO {
    private Integer id;
    private Integer xSize;
    private Integer ySize;
    private List<FieldDTO> fieldsInBoard = new ArrayList<FieldDTO>();
}
