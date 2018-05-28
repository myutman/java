package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class HardCPUPlayerTest {
    @Test
    public void forallWin() throws Exception {
        assertTrue(HardCPUPlayer.forallWin(new GameState[][] {
                new GameState[] {GameState.X, GameState.None, GameState.O},
                new GameState[] {GameState.O, GameState.None, GameState.None},
                new GameState[] {GameState.X, GameState.None, GameState.X}
        }, GameState.X));
    }

    @Test
    public void notForallWin() throws Exception {
        assertFalse(HardCPUPlayer.forallWin(new GameState[][] {
                new GameState[] {GameState.X, GameState.None, GameState.None},
                new GameState[] {GameState.None, GameState.O, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.X}
        }, GameState.X));
    }

    @Test
    public void existsWin() throws Exception {
        assertTrue(HardCPUPlayer.existsWin(new GameState[][] {
                new GameState[] {GameState.X, GameState.X, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.O}
        }, GameState.O));
    }

    @Test
    public void notExistsWin() throws Exception {
        assertFalse(HardCPUPlayer.existsWin(new GameState[][] {
                new GameState[] {GameState.None, GameState.None, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.None}
        }, GameState.O));
    }

    @Test
    public void forallDraw() throws Exception {
        assertTrue(HardCPUPlayer.forallDraw(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.X},
                new GameState[] {GameState.X, GameState.O, GameState.O},
                new GameState[] {GameState.X, GameState.O, GameState.None}
        }, GameState.O));
    }

    @Test
    public void notForallDraw() throws Exception {
        assertFalse(HardCPUPlayer.forallDraw(new GameState[][] {
                new GameState[] {GameState.None, GameState.None, GameState.None},
                new GameState[] {GameState.X, GameState.O, GameState.X},
                new GameState[] {GameState.None, GameState.None, GameState.None}
        }, GameState.X));
    }

    @Test
    public void existsDraw() throws Exception {
        assertTrue(HardCPUPlayer.forallDraw(new GameState[][] {
                new GameState[] {GameState.X, GameState.None, GameState.None},
                new GameState[] {GameState.None, GameState.O, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.None}
        }, GameState.X));
    }

    @Test
    public void notExistsDraw() throws Exception {
        assertFalse(HardCPUPlayer.forallDraw(new GameState[][] {
                new GameState[] {GameState.O, GameState.None, GameState.O},
                new GameState[] {GameState.X, GameState.None, GameState.None},
                new GameState[] {GameState.X, GameState.X, GameState.O}
        }, GameState.X));
    }

}