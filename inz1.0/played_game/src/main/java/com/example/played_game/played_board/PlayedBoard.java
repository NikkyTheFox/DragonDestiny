package com.example.played_game.played_board;


import com.example.played_game.played_field.PlayedField;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponds to board used in ONE played game.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Document(collection = "Board")
public class PlayedBoard implements Serializable
{
    /**
     * Identifier of field.
     */
    @Id
    private Integer id;
    /**
     * Number of fields in x-dimension.
     */
//    private Integer xSize;
    /**
     * Number of fields in u-dimension.
     */
//    private Integer ySize;
    /**
     * List of fields in the board - one board => many fields.
     */
    @OneToMany(mappedBy = "id")
    private List<PlayedField> fieldsOnBoard = new ArrayList<>();

    /**
     * Method for adding fields to the board during initialization of played game.
     * @param field
     */
    public void addFieldsInBoard(PlayedField field)
    {
        this.fieldsOnBoard.add(field);
    }


}
