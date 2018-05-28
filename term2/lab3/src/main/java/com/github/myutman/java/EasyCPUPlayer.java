package com.github.myutman.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Easy bot.
 */
public class EasyCPUPlayer implements Player {

    private GameState side;
    private Random random;

    public EasyCPUPlayer(GameState side) {
        this.side = side;
        random = new Random(1488239228);
    }

    @Override
    public void makeTurn(Controller controller) {
        ArrayList<Cell> freeCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (controller.getState()[i][j].equals(GameState.None)) {
                    freeCells.add(new Cell(i, j));
                }
            }
        }
        int k = random.nextInt(freeCells.size());
        int x = freeCells.get(k).getX();
        int y = freeCells.get(k).getY();
        controller.set(x, y, side);
        controller.changeTurn();
    }
}
