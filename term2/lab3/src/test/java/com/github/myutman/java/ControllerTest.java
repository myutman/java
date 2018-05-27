package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 3/23/18.
 */
public class ControllerTest {

    @Test
    public void xWinsMainDiagonal() throws Exception {
        assertTrue(Controller.xWins(new int[][] {
                new int[] {1, 2, 2},
                new int[] {2, 1, 0},
                new int[] {1, 0, 1}
        }));
    }

    @Test
    public void xWinsCollateralDiagonal() throws Exception {
        assertTrue(Controller.xWins(new int[][] {
                new int[] {0, 2, 1},
                new int[] {2, 1, 0},
                new int[] {1, 0, 0}
        }));
    }

    @Test
    public void xWinsHorisontal() throws Exception {
        assertTrue(Controller.xWins(new int[][] {
                new int[] {0, 2, 2},
                new int[] {1, 1, 1},
                new int[] {0, 0, 0}
        }));
    }

    @Test
    public void xWinsVertical() throws Exception {
        assertTrue(Controller.xWins(new int[][] {
                new int[] {2, 1, 1},
                new int[] {1, 2, 1},
                new int[] {2, 2, 1}
        }));
    }

    @Test
    public void xLoses() throws Exception {
        assertFalse(Controller.xWins(new int[][] {
                new int[] {2, 1, 1},
                new int[] {1, 2, 1},
                new int[] {2, 1, 2}
        }));
    }

    @Test
    public void xDraws() throws Exception {
        assertFalse(Controller.xWins(new int[][] {
                new int[] {2, 1, 2},
                new int[] {1, 2, 1},
                new int[] {1, 2, 1}
        }));
    }

    @Test
    public void oWinsMainDiagonal() throws Exception {
        assertTrue(Controller.oWins(new int[][] {
                new int[] {2, 1, 1},
                new int[] {1, 2, 0},
                new int[] {0, 0, 2}
        }));
    }

    @Test
    public void oWinsCollateralDiagonal() throws Exception {
        assertTrue(Controller.oWins(new int[][] {
                new int[] {0, 1, 2},
                new int[] {1, 2, 0},
                new int[] {2, 0, 1}
        }));
    }

    @Test
    public void oWinsHorisontal() throws Exception {
        assertTrue(Controller.oWins(new int[][] {
                new int[] {0, 1, 1},
                new int[] {2, 2, 2},
                new int[] {0, 1, 0}
        }));
    }

    @Test
    public void oWinsVertical() throws Exception {
        assertTrue(Controller.oWins(new int[][] {
                new int[] {1, 2, 0},
                new int[] {1, 2, 1},
                new int[] {2, 2, 1}
        }));
    }

    @Test
    public void oLoses() throws Exception {
        assertFalse(Controller.oWins(new int[][] {
                new int[] {2, 1, 1},
                new int[] {1, 1, 2},
                new int[] {2, 1, 2}
        }));
    }

    @Test
    public void oDraws() throws Exception {
        assertFalse(Controller.oWins(new int[][] {
                new int[] {2, 1, 2},
                new int[] {1, 2, 1},
                new int[] {1, 2, 1}
        }));
    }

    @Test
    public void notFull() throws Exception {
        assertFalse(Controller.isFieldFull(new int[][] {
                new int[] {2, 0, 1},
                new int[] {1, 1, 2},
                new int[] {2, 1, 2}
        }));
    }

    @Test
    public void full() throws Exception {
        assertTrue(Controller.isFieldFull(new int[][] {
                new int[] {2, 2, 1},
                new int[] {1, 1, 2},
                new int[] {2, 1, 1}
        }));
    }

}