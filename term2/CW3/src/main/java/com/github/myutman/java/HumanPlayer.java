package com.github.myutman.java;

/**
 * Human player.
 */
public class HumanPlayer implements Player {

    private int side;

    /**
     * Class constructor.
     * @param side what figures does he play
     */
    public HumanPlayer(int side) {
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
