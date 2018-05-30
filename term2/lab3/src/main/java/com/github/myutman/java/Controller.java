package com.github.myutman.java;

import com.sun.istack.internal.NotNull;

/**
 * Simulates game between two players.
 */
public class Controller {

    /**
     * Whose turn is it now.
     */
    private GameState turn;
    private GameState state[][] = new GameState[3][3];
    private Player playerX;
    private Player playerO;
    private GameState result;

    /**
     * Class constructor.
     */
    public Controller() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = GameState.None;
            }
        }
    }

    /**
     * Returns result of the game.
     * @return GameState.Draw if it is draw, GameState.X if X wins, GameState.O if O wins or GameState.None if game is not over
     */
    @NotNull
    public GameState result() {
        return result;
    }

    /**
     * Turn goes to next player.
     */
    public synchronized void changeTurn() {
        if (turn.equals(GameState.None) || turn.equals(GameState.Draw)) throw new UnsupportedOperationException();
        if (turn.equals(GameState.X)) turn = GameState.O;
        else if (turn.equals(GameState.O)) turn = GameState.X;
        notifyAll();
    }

    @NotNull
    public GameState getTurn() {
        return turn;
    }

    @NotNull
    public GameState[][] getState(){
        return state;
    }

    /**
     * Sets value in requested position of requested row.
     * @param i row
     * @param j column
     * @param side value
     */
    public void set(int i, int j, GameState side) {
        state[i][j] = side;
    }

    /**
     * Simulates the game between two players.
     * @param playerX first player
     * @param playerO second player
     */
    public synchronized void startGame(Player playerX, Player playerO) {
        result = GameState.None;
        this.playerX = playerX;
        this.playerO = playerO;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = GameState.None;
            }
        }
        notifyAll();
        turn = GameState.X;
        while (!gameOver()) {
            if (turn.equals(GameState.X)) {
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
        if (turn.equals(GameState.X)) return playerX instanceof HumanPlayer;
        return playerO instanceof HumanPlayer;
    }

    /**
     * Checks if specified player is the winner.
     * @param player player needed to be ckecked
     * @param state current position
     * @return true if X wins and false otherwise
     */
    public static boolean wins(GameState[][] state, GameState player) {
        if (player.equals(GameState.None) || player.equals(GameState.Draw)) return false;
        for (int i = 0; i < 3; i++) {
            boolean flag = true;
            for (int j = 1; j < 3; j++) {
                if (!state[i][j].equals(state[i][0])) flag = false;
            }
            if (flag) {
                if (state[i][0].equals(player)) {
                    return true;
                }
            }
            flag = true;
            for (int j = 1; j < 3; j++) {
                if (!state[j][i].equals(state[0][i])) flag = false;
            }
            if (flag) {
                if (state[0][i].equals(player)) {
                    return true;
                }
            }
        }
        boolean flag = true;
        for (int i = 1; i < 3; i++) {
            if  (!state[i][i].equals(state[0][0])) flag = false;
        }
        if (flag) {
            if (state[0][0].equals(player)) {
                return true;
            }
        }
        flag = true;
        for (int i = 1; i < 3; i++) {
            if (!state[2 - i][i].equals(state[2][0])) flag = false;
        }
        return flag && state[2][0].equals(player);
    }

    /**
     * Checks if the field is full.
     * @param state current position
     * @return true if it the field is full and false otherwise
     */
    public static boolean isFieldFull(GameState[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals(GameState.None)) return false;
            }
        }
        return true;
    }

    /**
     * Checks if game is over.
     * @return true if game is over and false otherwise
     */
    public boolean gameOver() {
        if (wins(state, GameState.X)) {
            result = GameState.X;
            return true;
        }
        if (wins(state, GameState.O)) {
            result = GameState.O;
            return true;
        }
        if (isFieldFull(state)) {
            result = GameState.Draw;
            return true;
        }
        return false;
    }
}
