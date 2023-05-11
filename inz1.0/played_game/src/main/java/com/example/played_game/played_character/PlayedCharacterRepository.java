package com.example.played_game.played_character;


import org.springframework.stereotype.Repository;
import com.example.played_game.played_character.PlayedCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface PlayedCharacterRepository extends MongoRepository<PlayedCharacter, Integer> {

}
