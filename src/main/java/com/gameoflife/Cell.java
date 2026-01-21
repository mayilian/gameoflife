package com.gameoflife;

/**
 * Represents the state of a cell in the Game of Life.
 */
public enum Cell {
    ALIVE,
    DEAD;

    public boolean isAlive() {
        return this == ALIVE;
    }
}
