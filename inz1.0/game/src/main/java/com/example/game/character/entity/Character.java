package com.example.game.character.entity;


import com.example.game.field.entity.Field;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a characters cards that are available to play.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Table(name = "characters")
public class Character
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Integer id;
    private String name;
    private String profession;
    private String story;
    @Column(name = "initial_strength")
    private Integer initialStrength;
    @Column(name = "initial_health")
    private Integer initialHealth;

    Integer gameId;

}
