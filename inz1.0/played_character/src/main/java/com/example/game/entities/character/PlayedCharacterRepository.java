package com.example.game.entities.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayedCharacterRepository extends JpaRepository<PlayedCharacter, Integer> {

}

