package pl.edu.pg.eti.dragondestiny.playedgame.interfaces;

/**
 * Interface needed to implement generic method to check whether an object instance (enemy or player) is dead or alive.
 */
public interface HealthCalculable {

    /**
     * Template of a method which will calculate total remaining health of an object instance.
     * @return A number informing about amount of health remaining.
     */
    Boolean isAlive();
}
