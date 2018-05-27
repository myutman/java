package com.github.myutman.java;

/**
 * Hard bot.
 */
public class HardCPUPlayer extends EasyCPUPlayer {

    private int side;

    public HardCPUPlayer(int side) {
        super(side);
        this.side = side;
    }

    /**
     * Check if you can win regardless to opponent moves (opponent turn).
     * @param state current position
     * @param type figures you play
     * @return true if you can win or false otherwise
     */
    public static boolean forallWin(int[][] state, int type) {
        if (Controller.xWins(state)) {
            return type == 1;
        }
        if (Controller.oWins(state)) {
            return type == 2;
        }
        if (Controller.isFieldFull(state)) return false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    state[i][j] = 3 - type;
                    boolean b = existsWin(state, type);
                    state[i][j] = 0;
                    if (!b) return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if you can win regardless to opponent moves (your turn).
     * @param state current position
     * @param type figures you play
     * @return true if you can win or false otherwise
     */
    public static boolean existsWin(int[][] state, int type) {
        if (Controller.xWins(state)) {
            return type == 1;
        }
        if (Controller.oWins(state)) {
            return type == 2;
        }
        if (Controller.isFieldFull(state)) return false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    state[i][j] = type;
                    boolean b = forallWin(state, type);
                    state[i][j] = 0;
                    if (b) return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if you can draw regardless to opponent moves (opponent turn).
     * @param state current position
     * @param type figures you play
     * @return true if you can win or false otherwise
     */
    public static boolean forallDraw(int[][] state, int type) {
        if (Controller.xWins(state)) {
            return type == 1;
        }
        if (Controller.oWins(state)) {
            return type == 2;
        }
        if (Controller.isFieldFull(state)) return true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    state[i][j] = 3 - type;
                    boolean b = existsDraw(state, type);
                    state[i][j] = 0;
                    if (!b) return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if you can draw regardless to opponent moves (your turn).
     * @param state current position
     * @param type figures you play
     * @return true if you can win or false otherwise
     */
    public static boolean existsDraw(int[][] state, int type) {
        if (Controller.xWins(state)) {
            return type == 1;
        }
        if (Controller.oWins(state)) {
            return type == 2;
        }
        if (Controller.isFieldFull(state)) return true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    state[i][j] = type;
                    boolean b = forallDraw(state, type);
                    state[i][j] = 0;
                    if (b) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void makeTurn(Controller controller) {
        int[][] state = controller.getState().clone();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    state[i][j] = side;
                    boolean b = forallWin(state, side);
                    state[i][j] = 0;
                    if (b) {
                        controller.set(i, j, side);
                        controller.changeTurn();
                        return;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    state[i][j] = side;
                    boolean b = forallDraw(state, side);
                    state[i][j] = 0;
                    if (b) {
                        controller.set(i, j, side);
                        controller.changeTurn();
                        return;
                    }
                }
            }
        }
        super.makeTurn(controller);
    }
}
