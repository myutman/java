package com.github.myutman.java;

import javafx.application.Platform;
import sun.nio.ch.ThreadPool;

import java.util.ArrayList;
import java.util.Random;

/**
 * Simulates game between two players.
 */
public class Controller {

    private boolean turn;
    private boolean frozen;
    private int field[][];
    private boolean opened[][];
    private int n;
    private int numberOpened;
    private int curI, curJ;
    private int lastI, lastJ;

    /**
     * Class constructor.
     */
    public Controller(int n) {
        this.n = n;
        field = new int[n][n];
        opened = new boolean[n][n];
    }

    public boolean isFrozen() {
        return frozen;
    }

    public int[][] getField(){
        return field;
    }

    public boolean[][] getOpened(){
        return opened;
    }

    /**
     * Making turn to pos (i, j).
     */
    public synchronized void makeTurn(int i, int j) {
        if (!opened[i][j]) {
            opened[i][j] = true;
            Main.updatePos(i, j);
            if (!turn) {
                curI = i;
                curJ = j;
            } else {
                if (field[curI][curJ] != field[i][j]) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            opened[i][j] = false;
                            Main.updatePos(i, j);
                            opened[curI][curJ] = false;
                            Main.updatePos(curI, curJ);
                            curI = -1;
                            curJ = -1;
                        });
                    }).start();
                } else {
                    numberOpened += 2;
                }
            }
            turn = !turn;
            notifyAll();
        }
    }

    /**
     * Simulates the game.
     */
    public synchronized void startGame() {
        curI = -1;
        curJ = -1;
        numberOpened = 0;
        turn = false;
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < n * n / 2; i++) {
            arrayList.add(i);
            arrayList.add(i);
        }
        Random random = new Random(1488239228);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int pos = random.nextInt(arrayList.size());
                field[i][j] = arrayList.get(pos);
                arrayList.remove(pos);
            }
        }
        Main.initScreen();
        notifyAll();
        new Thread(() -> {
            while (!gameOver()) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Checks if game is over.
     * @return true if game is over and false otherwise
     */
    public boolean gameOver() {
        return numberOpened == n * n;
    }
}
