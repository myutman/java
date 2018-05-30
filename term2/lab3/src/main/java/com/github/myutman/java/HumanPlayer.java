package com.github.myutman.java;

/**
 * Human player.
 */
public class HumanPlayer implements Player {

    private GameState side;

    /**
     * Class constructor.
     * @param side what figures does he play
     */
    public HumanPlayer(GameState side) {
        this.side = side;
    }

    @Override
    public void makeTurn(Controller controller) {
        synchronized (controller) {
            while (controller.getTurn() == side) {
                try {
                    controller.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
