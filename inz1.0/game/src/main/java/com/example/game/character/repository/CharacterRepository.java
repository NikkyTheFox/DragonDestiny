package com.example.game.character.repository;

import com.example.game.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type Character and Integer as id
 */
@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {

    Character findCharacterByGameIdAndId(Integer gameId, Integer id);

    List<Character> findCharactersByGameId(Integer gameId);

}
