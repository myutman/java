package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 3/23/18.
 */
public class HardCPUPlayerTest {
    @Test
    public void forallWin() throws Exception {
        assertTrue(HardCPUPlayer.forallWin(new int[][] {
                new int[] {1, 0, 2},
                new int[] {2, 0, 0},
                new int[] {1, 0, 1}
        }, 1));
    }

    @Test
    public void notForallWin() throws Exception {
        assertFalse(HardCPUPlayer.forallWin(new int[][] {
                new int[] {1, 0, 0},
                new int[] {0, 2, 0},
                new int[] {0, 0, 1}
        }, 1));
    }

    @Test
    public void existsWin() throws Exception {
        assertTrue(HardCPUPlayer.existsWin(new int[][] {
                new int[] {1, 1, 0},
                new int[] {0, 0, 0},
                new int[] {0, 0, 2}
        }, 2));
    }

    @Test
    public void notExistsWin() throws Exception {
        assertFalse(HardCPUPlayer.existsWin(new int[][] {
                new int[] {0, 0, 0},
                new int[] {0, 0, 0},
                new int[] {0, 0, 0}
        }, 2));
    }

    @Test
    public void forallDraw() throws Exception {
        assertTrue(HardCPUPlayer.forallDraw(new int[][] {
                new int[] {2, 1, 1},
                new int[] {1, 2, 2},
                new int[] {1, 2, 0}
        }, 2));
    }

    @Test
    public void notForallDraw() throws Exception {
        assertFalse(HardCPUPlayer.forallDraw(new int[][] {
                new int[] {0, 0, 0},
                new int[] {1, 2, 1},
                new int[] {0, 0, 0}
        }, 1));
    }

    @Test
    public void existsDraw() throws Exception {
        assertTrue(HardCPUPlayer.forallDraw(new int[][] {
                new int[] {1, 0, 0},
                new int[] {0, 2, 0},
                new int[] {0, 0, 0}
        }, 1));
    }

    @Test
    public void notExistsDraw() throws Exception {
        assertFalse(HardCPUPlayer.forallDraw(new int[][] {
                new int[] {2, 0, 2},
                new int[] {1, 0, 0},
                new int[] {1, 1, 2}
        }, 1));
    }

}