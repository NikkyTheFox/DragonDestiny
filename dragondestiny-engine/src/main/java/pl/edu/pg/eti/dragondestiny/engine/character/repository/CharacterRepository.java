package pl.edu.pg.eti.dragondestiny.engine.character.repository;

import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPARepository interface with domain type Character and Integer as id.
 */
@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {

    /**
     * Method to retrieve character by game and characterId - one character from all in particular game.
     *
     * @param game A game to find character from,
     * @param characterId An identifier of character to be retrieved.
     * @return A character.
     */
    Optional<Character> findByGamesAndId(Game game, Integer characterId);

    /**
     * Method to retrieve all characters by game - all characters in particular game.
     *
     * @param game A game to find all characters from.
     * @return A list of characters in game.
     */
    List<Character> findAllByGames(Game game);

}
