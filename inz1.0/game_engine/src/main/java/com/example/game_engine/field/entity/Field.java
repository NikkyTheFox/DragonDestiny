package com.example.game_engine.field.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * Represent a field (of possible board) in the game.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Table(name = "fields")
public class Field
{
    /**
     * Unique identifier of field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Integer id;

    /**
     * Type of field - describes the function of the field.
     */
    @Column(name = "field_type")
    @Enumerated(EnumType.STRING)
    private FieldTypesEnum fieldType;

    /**
     * xPosition is equal to 0 (first row) or Board.ySize (last row).
     */
    @Column(name = "x_position")

    private Integer xPosition;
    /**
     * yPosition is equal to 0 (first column) or Board.xSize (last column).
     */
    @Column(name = "y_position")
    private Integer yPosition;

    /**
     * Identifier of board the field belongs to.
     */
    private Integer boardId;
}
