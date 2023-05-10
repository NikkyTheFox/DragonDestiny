package com.example.played_game.played_character;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.played_game.played_character.PlayedCharacter;

@Repository
public interface PlayedCharacterRepository extends JpaRepository<PlayedCharacter, Integer> {

}
