package com.gameoflife;

/**
 * Implements Conway's Game of Life rules.
 *
 * Rules:
 * 1. A living cell with fewer than 2 living neighbors dies (underpopulation)
 * 2. A living cell with 2 or 3 living neighbors stays alive
 * 3. A living cell with more than 3 living neighbors dies (overpopulation)
 * 4. A dead cell with exactly 3 living neighbors comes to life (reproduction)
 */
public class GameRules {

    /**
     * Determines the next state of a cell based on its current state and neighbor count.
     */
    public Cell getNextState(Cell currentState, int aliveNeighbors) {
        if (currentState.isAlive()) {
            // Rules 1, 2, 3: Living cell survival
            if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                return Cell.DEAD;
            }
            return Cell.ALIVE;
        } else {
            // Rule 4: Dead cell reproduction
            if (aliveNeighbors == 3) {
                return Cell.ALIVE;
            }
            return Cell.DEAD;
        }
    }
}
