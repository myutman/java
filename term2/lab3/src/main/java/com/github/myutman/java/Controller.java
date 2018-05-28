package com.github.myutman.java;

/**
 * Simulates game between two players.
 */
public class Controller {

    /**
     * Whose turn is it now.
     */
    private int turn;
    private int state[][] = new int[3][3];
    private Player playerX;
    private Player playerO;
    private GameResult result;

    /**
     * Class constructor.
     */
    public Controller() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = 0;
            }
        }
    }

    /**
     * Returns result of the game.
     * @return GameResult.Draw if it is draw, GameResult.WinX if X wins, GameResult.WinO if O wins or GameResult.None if game is not over
     */
    public GameResult result() {
        return result;
    }

    /**
     * Turn goes to next player.
     */
    public synchronized void changeTurn() {
        turn = 3 - turn;
        notifyAll();
    }

    public int getTurn() {
        return turn;
    }

    public int[][] getState(){
        return state;
    }

    /**
     * Sets value in requested position of requested row.
     * @param i row
     * @param j column
     * @param side value
     */
    public void set(int i, int j, int side) {
        state[i][j] = side;
    }

    /**
     * Simulates the game between two players.
     * @param playerX first player
     * @param playerO second player
     */
    public synchronized void startGame(Player playerX, Player playerO) {
        result = GameResult.None;
        this.playerX = playerX;
        this.playerO = playerO;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = 0;
            }
        }
        notifyAll();
        turn = 1;
        while (!gameOver()) {
            if (turn == 1) {
                playerX.makeTurn(this);
            } else {
                playerO.makeTurn(this);
            }
        }
    }

    /**
     * Checks if current player is bot.
     * @return true if he is bot and false otherwise
     */
    public boolean isCurrentPlayerHuman() {
        if (turn == 1) return playerX instanceof HumanPlayer;
        return playerO instanceof HumanPlayer;
    }

    /**
     * Checks if player X is the winner.
     * @param state current position
     * @return true if X wins and false otherwise
     */
    public static boolean xWins(int[][] state) {
        for (int i = 0; i < 3; i++) {
            boolean flag = true;
            for (int j = 1; j < 3; j++) {
                if (state[i][j] != state[i][0]) flag = false;
            }
            if (flag) {
                if (state[i][0] == 1) {
                    return true;
                }
            }
            flag = true;
            for (int j = 1; j < 3; j++) {
                if (state[j][i] != state[0][i]) flag = false;
            }
            if (flag) {
                if (state[0][i] == 1) {
                    return true;
                }
            }
        }
        boolean flag = true;
        for (int i = 1; i < 3; i++) {
            if  (state[i][i] != state[0][0]) flag = false;
        }
        if (flag) {
            if (state[0][0] == 1) {
                return true;
            }
        }
        flag = true;
        for (int i = 1; i < 3; i++) {
            if (state[2 - i][i] != state[2][0]) flag = false;
        }
        if (flag) {
            if (state[2][0] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if player O is the winner.
     * @param state current position
     * @return true if O wins and false otherwise
     */
    public static boolean oWins(int[][] state) {
        for (int i = 0; i < 3; i++) {
            boolean flag = true;
            for (int j = 1; j < 3; j++) {
                if (state[i][j] != state[i][0]) flag = false;
            }
            if (flag && state[i][0] == 2) {
                return true;
            }
            flag = true;
            for (int j = 1; j < 3; j++) {
                if (state[j][i] != state[0][i]) flag = false;
            }
            if (flag && state[0][i] == 2) {
                return true;
            }
        }
        boolean flag = true;
        for (int i = 1; i < 3; i++) {
            if (state[i][i] != state[0][0]) flag = false;
        }
        if (flag && state[0][0] == 2) {
            return true;
        }
        flag = true;
        for (int i = 1; i < 3; i++) {
            if (state[2 - i][i] != state[2][0]) flag = false;
        }
        return flag && state[2][0] == 2;
    }

    /**
     * Checks if the field is full.
     * @param state current position
     * @return true if it the field is full and false otherwise
     */
    public static boolean isFieldFull(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) return false;
            }
        }
        return true;
    }

    /**
     * Checks if game is over.
     * @return true if game is over and false otherwise
     */
    public boolean gameOver() {
        if (xWins(state)) {
            result = GameResult.WinX;
            return true;
        }
        if (oWins(state)) {
            result = GameResult.WinO;
            return true;
        }
        if (isFieldFull(state)) {
            result = GameResult.Draw;
            return true;
        }
        return false;
    }
}
