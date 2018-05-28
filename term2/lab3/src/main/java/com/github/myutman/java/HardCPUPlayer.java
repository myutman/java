package com.github.myutman.java;

/**
 * Hard bot.
 */
public class HardCPUPlayer extends EasyCPUPlayer {

    private GameState side;

    public HardCPUPlayer(GameState side) {
        super(side);
        this.side = side;
    }

    /**
     * Check if you can win regardless to opponent moves (opponent turn).
     * @param state current position
     * @param type figures you play
     * @return true if you can win or false otherwise
     */
    public static boolean forallWin(GameState[][] state, GameState type) {
        if (Controller.xWins(state)) {
            return type.equals(GameState.X);
        }
        if (Controller.oWins(state)) {
            return type.equals(GameState.O);
        }
        if (Controller.isFieldFull(state)) return false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals(GameState.None)) {
                    if (type.equals(GameState.X)) state[i][j] = GameState.O;
                    else state[i][j] = GameState.X;
                    boolean b = existsWin(state, type);
                    state[i][j] = GameState.None;
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
    public static boolean existsWin(GameState[][] state, GameState type) {
        if (Controller.xWins(state)) {
            return type.equals(GameState.X);
        }
        if (Controller.oWins(state)) {
            return type.equals(GameState.O);
        }
        if (Controller.isFieldFull(state)) return false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals(GameState.None)) {
                    state[i][j] = type;
                    boolean b = forallWin(state, type);
                    state[i][j] = GameState.None;
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
    public static boolean forallDraw(GameState[][] state, GameState type) {
        if (Controller.xWins(state)) {
            return type.equals(GameState.X);
        }
        if (Controller.oWins(state)) {
            return type.equals(GameState.O);
        }
        if (Controller.isFieldFull(state)) return true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == GameState.None) {
                    if (type.equals(GameState.X)) state[i][j] = GameState.O;
                    else state[i][j] = GameState.X;
                    boolean b = existsDraw(state, type);
                    state[i][j] = GameState.None;
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
    public static boolean existsDraw(GameState[][] state, GameState type) {
        if (Controller.xWins(state)) {
            return type.equals(GameState.X);
        }
        if (Controller.oWins(state)) {
            return type.equals(GameState.O);
        }
        if (Controller.isFieldFull(state)) return true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals(GameState.None)) {
                    state[i][j] = type;
                    boolean b = forallDraw(state, type);
                    state[i][j] = GameState.None;
                    if (b) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void makeTurn(Controller controller) {
        GameState[][] state = controller.getState().clone();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals(GameState.None)) {
                    state[i][j] = side;
                    boolean b = forallWin(state, side);
                    state[i][j] = GameState.None;
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
                if (state[i][j].equals(GameState.None)) {
                    state[i][j] = side;
                    boolean b = forallDraw(state, side);
                    state[i][j] = GameState.None;
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
