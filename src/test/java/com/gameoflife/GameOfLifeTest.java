package com.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void testBlinkerOscillator() {
        // Blinker is a period-2 oscillator
        // Initial:     After 1 gen:    After 2 gen:
        //   .#.          ...             .#.
        //   .#.          ###             .#.
        //   .#.          ...             .#.

        GameOfLife game = new GameOfLife(5, 5);
        Grid grid = game.getGrid();

        // Set up vertical blinker
        grid.setCell(1, 2, Cell.ALIVE);
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(3, 2, Cell.ALIVE);

        // After one generation, should be horizontal
        game.nextGeneration();

        assertEquals(Cell.DEAD, grid.getCell(1, 2));
        assertEquals(Cell.ALIVE, grid.getCell(2, 1));
        assertEquals(Cell.ALIVE, grid.getCell(2, 2));
        assertEquals(Cell.ALIVE, grid.getCell(2, 3));
        assertEquals(Cell.DEAD, grid.getCell(3, 2));

        // After another generation, should be vertical again
        game.nextGeneration();

        assertEquals(Cell.ALIVE, grid.getCell(1, 2));
        assertEquals(Cell.ALIVE, grid.getCell(2, 2));
        assertEquals(Cell.ALIVE, grid.getCell(3, 2));
        assertEquals(Cell.DEAD, grid.getCell(2, 1));
        assertEquals(Cell.DEAD, grid.getCell(2, 3));
    }

    @Test
    void testBlock_StillLife() {
        // Block is a still life (doesn't change)
        // ##
        // ##

        GameOfLife game = new GameOfLife(4, 4);
        Grid grid = game.getGrid();

        grid.setCell(1, 1, Cell.ALIVE);
        grid.setCell(1, 2, Cell.ALIVE);
        grid.setCell(2, 1, Cell.ALIVE);
        grid.setCell(2, 2, Cell.ALIVE);

        // Should remain unchanged after multiple generations
        for (int i = 0; i < 5; i++) {
            game.nextGeneration();

            assertEquals(Cell.ALIVE, grid.getCell(1, 1));
            assertEquals(Cell.ALIVE, grid.getCell(1, 2));
            assertEquals(Cell.ALIVE, grid.getCell(2, 1));
            assertEquals(Cell.ALIVE, grid.getCell(2, 2));
        }
    }

    @Test
    void testGenerationCounter() {
        GameOfLife game = new GameOfLife(3, 3);

        assertEquals(0, game.getGeneration());

        game.nextGeneration();
        assertEquals(1, game.getGeneration());

        game.nextGeneration();
        assertEquals(2, game.getGeneration());
    }

    @Test
    void testRandomizeResetsGeneration() {
        GameOfLife game = new GameOfLife(5, 5);

        game.nextGeneration();
        game.nextGeneration();
        assertEquals(2, game.getGeneration());

        game.randomize(0.5);
        assertEquals(0, game.getGeneration());
    }

    @Test
    void testSingleCellDies() {
        // A single cell has no neighbors and dies
        GameOfLife game = new GameOfLife(3, 3);
        game.getGrid().setCell(1, 1, Cell.ALIVE);

        game.nextGeneration();

        assertEquals(Cell.DEAD, game.getGrid().getCell(1, 1));
    }

    @Test
    void testGetDimensions() {
        GameOfLife game = new GameOfLife(10, 20);
        assertEquals(10, game.getWidth());
        assertEquals(20, game.getHeight());
    }
}
