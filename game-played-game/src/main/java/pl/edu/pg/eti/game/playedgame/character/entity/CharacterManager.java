package pl.edu.pg.eti.game.playedgame.character.entity;

import pl.edu.pg.eti.game.playedgame.field.entity.Field;

public class CharacterManager extends Character {

    public Character setPositionField(Character character, Field field) {
        character.setPositionField(field);
        return character;
    }

    /**
     * Method to increase additional (received) strength points by val.
     *
     * @param val
     */
    public Character increaseStrength(Character character, Integer val) {
        character.setAdditionalStrength(character.getAdditionalStrength() + val);
        return character;
    }

    /**
     * Method to decrease additional (received) strength points by val.
     *
     * @param val
     */
    public Character decreaseStrength(Character character, Integer val) {
        character.setAdditionalStrength(character.getAdditionalStrength() - val);
        return character;
    }

    /**
     * Method to increase additional (received) health points by val.
     *
     * @param val
     */
    public Character increaseHealth(Character character, Integer val) {
        character.setAdditionalHealth(character.getAdditionalHealth() + val);
        return character;
    }

    /**
     * Method to decrease additional (received) health points by val.
     *
     * @param val
     */
    public Character decreaseHealth(Character character, Integer val) {
        character.setAdditionalHealth(character.getAdditionalHealth() - val);
        return character;
    }

}
