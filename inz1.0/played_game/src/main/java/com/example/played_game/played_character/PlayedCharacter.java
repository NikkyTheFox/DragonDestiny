package com.example.played_game.played_character;


import com.example.played_game.played_field.PlayedField;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Represents characters cards that are used in PLAYED GAME.
 */
@Data
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Document(collection = "Character")
public class PlayedCharacter implements Serializable
{
    /**
     * Identifier of character.
     */
    @Id
    private Integer id;
    /**
     * Initial strength of the character - minimum.
     */
    private Integer initialStrength;
    /**
     * Initial health of the character.
     */
    private Integer initialHealth;
    /**
     * Additional strength points received during the game - NOT FROM CARDS!.
     * Total strength of character is sum of initialStrength + additionalStrength.
     */
    private Integer additionalStrength;
    /**
     * Additional health points received during the game - NOT FROM CARDS!.
     * Can be negative.
     * Total number health points is sum of initialHealth + additionalHealth.
     * If sum <= 0, character is dead and user that plays that character ends the game.
     */
    private Integer additionalHealth;
    /**
     * Position of the character on the board - on particular field.
     * Many characters can stand on the same field.
     */
    @ManyToOne
    private PlayedField positionField;

    public PlayedCharacter()
    {
        this.additionalHealth = 0;
        this.additionalStrength = 0;
        this.positionField = null;
    }

}
