package com.github.myutman.java;

/**
 * Interface of game player.
 */
public interface Player {
    /**
     * Player makes one turn.
     * @param controller controller of the game
     */
    void makeTurn(Controller controller);
}
