package pl.edu.pg.eti.game.playedgame.character.entity;

import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;

public class CharacterManager extends Character {

    public Character setPositionField(Character character, Field field) {
        character.setField(field);
        return character;
    }

    /**
     * Method to increase or decrease additional (received) strength points by val.
     *
     * @param val
     */
    public Character addStrength(Character character, Integer val) {
        character.setReceivedStrength(character.getReceivedStrength() + val);
        return character;
    }

    /**
     * Method to increase or decrease additional (received) health points by val.
     *
     * @param val
     */
    public Character addHealth(Character character, Integer val) {
        character.setReceivedHealth(character.getReceivedHealth() + val);
        return character;
    }

    /**
     * Method to add strength and health points from card to character.
     *
     * @param character
     * @param card
     * @return
     */
    public Character addCard(Character character, ItemCard card) {
        character.setCardsHealth(character.getCardsHealth() + card.getHealth());
        character.setCardsStrength(character.getCardsStrength() + card.getStrength());
        return character;
    }

    /**
     * Method to remove strength and health points from card to character.
     *
     * @param character
     * @param card
     * @return
     */
    public Character removeCard(Character character, ItemCard card) {
        character.setCardsHealth(character.getCardsHealth() - card.getHealth());
        character.setCardsStrength(character.getCardsStrength() - card.getStrength());
        return character;
    }
}
