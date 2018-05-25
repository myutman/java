package com.github.myutman.java;

import java.net.Socket;

/**
 * Simulates game between two players.
 */
public class Controller {

    private int turn;
    private int state[][] = new int[3][3];
    private Player playerX;
    private Player playerO;
    private boolean winX;
    private boolean winO;

    public Socket getSocket() {
        return null;
    }

    public void closeSocket() {

    }

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
     * @return 0 if it is draw, 1 if X wins and 2 if O wins.
     */
    public int result() {
        if (winX) return 1;
        if (winO) return 2;
        return 0;
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
        winX = false;
        winO = false;
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
    public boolean isCurrentPlayerNotBot() {
        if (turn == 1) return HumanPlayer.class.isInstance(playerX);
        return HumanPlayer.class.isInstance(playerO);
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
            if (flag) {
                if (state[i][0] == 2) {
                    return true;
                }
            }
            flag = true;
            for (int j = 1; j < 3; j++) {
                if (state[j][i] != state[0][i]) flag = false;
            }
            if (flag) {
                if (state[0][i] == 2) {
                    return true;
                }
            }
        }
        boolean flag = true;
        for (int i = 1; i < 3; i++) {
            if  (state[i][i] != state[0][0]) flag = false;
        }
        if (flag) {
            if (state[0][0] == 2) {
                return true;
            }
        }
        flag = true;
        for (int i = 1; i < 3; i++) {
            if (state[2 - i][i] != state[2][0]) flag = false;
        }
        if (flag) {
            if (state[2][0] == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the field is full.
     * @param state current position
     * @return true if it the field is full and false otherwise
     */
    public static boolean draw(int[][] state) {
        boolean flag = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) flag = false;
            }
        }
        return flag;
    }

    /**
     * Checks if game is over.
     * @return true if game is over and false otherwise
     */
    public boolean gameOver() {
        if (xWins(state)) {
            winX = true;
            return true;
        }
        if (oWins(state)) {
            winO = true;
            return true;
        }
        if (draw(state)) {
            return true;
        }
        return false;
    }
}
