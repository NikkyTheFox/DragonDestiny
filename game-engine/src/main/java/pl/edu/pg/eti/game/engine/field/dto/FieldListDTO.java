package pl.edu.pg.eti.game.engine.field.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FieldListDTO {

    private List<FieldDTO> fieldList;

}
