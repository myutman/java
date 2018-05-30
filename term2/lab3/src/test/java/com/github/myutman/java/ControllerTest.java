package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void xWinsMainDiagonal() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.X, GameState.O, GameState.O},
                new GameState[] {GameState.O, GameState.X, GameState.None},
                new GameState[] {GameState.X, GameState.None, GameState.X}
        }, GameState.X));
    }

    @Test
    public void xWinsCollateralDiagonal() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.None, GameState.O, GameState.X},
                new GameState[] {GameState.O, GameState.X, GameState.None},
                new GameState[] {GameState.X, GameState.None, GameState.None}
        }, GameState.X));
    }

    @Test
    public void xWinsHorisontal() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.None, GameState.O, GameState.O},
                new GameState[] {GameState.X, GameState.X, GameState.X},
                new GameState[] {GameState.None, GameState.None, GameState.None}
        }, GameState.X));
    }

    @Test
    public void xWinsVertical() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.X},
                new GameState[] {GameState.X, GameState.O, GameState.X},
                new GameState[] {GameState.O, GameState.O, GameState.X}
        }, GameState.X));
    }

    @Test
    public void xLoses() throws Exception {
        assertFalse(Controller.wins(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.X},
                new GameState[] {GameState.X, GameState.O, GameState.X},
                new GameState[] {GameState.O, GameState.X, GameState.O}
        }, GameState.X));
    }

    @Test
    public void xDraws() throws Exception {
        assertFalse(Controller.wins(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.O},
                new GameState[] {GameState.X, GameState.O, GameState.X},
                new GameState[] {GameState.X, GameState.O, GameState.X}
        }, GameState.X));
    }

    @Test
    public void oWinsMainDiagonal() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.X},
                new GameState[] {GameState.X, GameState.O, GameState.None},
                new GameState[] {GameState.None, GameState.None, GameState.O}
        }, GameState.O));
    }

    @Test
    public void oWinsCollateralDiagonal() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.None, GameState.X, GameState.O},
                new GameState[] {GameState.X, GameState.O, GameState.None},
                new GameState[] {GameState.O, GameState.None, GameState.X}
        }, GameState.O));
    }

    @Test
    public void oWinsHorisontal() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.None, GameState.X, GameState.X},
                new GameState[] {GameState.O, GameState.O, GameState.O},
                new GameState[] {GameState.None, GameState.X, GameState.None}
        }, GameState.O));
    }

    @Test
    public void oWinsVertical() throws Exception {
        assertTrue(Controller.wins(new GameState[][] {
                new GameState[] {GameState.X, GameState.O, GameState.None},
                new GameState[] {GameState.X, GameState.O, GameState.X},
                new GameState[] {GameState.O, GameState.O, GameState.X}
        }, GameState.O));
    }

    @Test
    public void oLoses() throws Exception {
        assertFalse(Controller.wins(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.X},
                new GameState[] {GameState.X, GameState.X, GameState.O},
                new GameState[] {GameState.O, GameState.X, GameState.O}
        }, GameState.O));
    }

    @Test
    public void oDraws() throws Exception {
        assertFalse(Controller.wins(new GameState[][] {
                new GameState[] {GameState.O, GameState.X, GameState.O},
                new GameState[] {GameState.X, GameState.O, GameState.X},
                new GameState[] {GameState.X, GameState.O, GameState.X}
        }, GameState.O));
    }

    @Test
    public void notFull() throws Exception {
        assertFalse(Controller.isFieldFull(new GameState[][] {
                new GameState[] {GameState.O, GameState.None, GameState.X},
                new GameState[] {GameState.X, GameState.X, GameState.O},
                new GameState[] {GameState.O, GameState.X, GameState.O}
        }));
    }

    @Test
    public void full() throws Exception {
        assertTrue(Controller.isFieldFull(new GameState[][] {
                new GameState[] {GameState.O, GameState.O, GameState.X},
                new GameState[] {GameState.X, GameState.X, GameState.O},
                new GameState[] {GameState.O, GameState.X, GameState.X}
        }));
    }

}