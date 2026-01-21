package com.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatternTest {

    @Test
    void testBlinkerPattern() {
        Grid grid = new Grid(5, 5);
        Pattern.BLINKER.applyTo(grid);

        // Blinker is vertical: cells at (0,1), (1,1), (2,1)
        assertEquals(Cell.ALIVE, grid.getCell(0, 1));
        assertEquals(Cell.ALIVE, grid.getCell(1, 1));
        assertEquals(Cell.ALIVE, grid.getCell(2, 1));
    }

    @Test
    void testBlinkerOscillates() {
        GameOfLife game = new GameOfLife(5, 5);
        Pattern.BLINKER.applyTo(game.getGrid());

        // After 1 generation: horizontal
        game.nextGeneration();
        Grid grid = game.getGrid();
        assertEquals(Cell.ALIVE, grid.getCell(1, 0));
        assertEquals(Cell.ALIVE, grid.getCell(1, 1));
        assertEquals(Cell.ALIVE, grid.getCell(1, 2));
        assertEquals(Cell.DEAD, grid.getCell(0, 1));
        assertEquals(Cell.DEAD, grid.getCell(2, 1));

        // After 2 generations: back to vertical
        game.nextGeneration();
        assertEquals(Cell.ALIVE, grid.getCell(0, 1));
        assertEquals(Cell.ALIVE, grid.getCell(1, 1));
        assertEquals(Cell.ALIVE, grid.getCell(2, 1));
    }

    @Test
    void testBlockPattern() {
        Grid grid = new Grid(4, 4);
        Pattern.BLOCK.applyTo(grid);

        // Block is 2x2: cells at (1,1), (1,2), (2,1), (2,2)
        assertEquals(Cell.ALIVE, grid.getCell(1, 1));
        assertEquals(Cell.ALIVE, grid.getCell(1, 2));
        assertEquals(Cell.ALIVE, grid.getCell(2, 1));
        assertEquals(Cell.ALIVE, grid.getCell(2, 2));
    }

    @Test
    void testBlockIsStillLife() {
        GameOfLife game = new GameOfLife(4, 4);
        Pattern.BLOCK.applyTo(game.getGrid());

        // Block should never change
        for (int i = 0; i < 10; i++) {
            game.nextGeneration();
            Grid grid = game.getGrid();
            assertEquals(Cell.ALIVE, grid.getCell(1, 1));
            assertEquals(Cell.ALIVE, grid.getCell(1, 2));
            assertEquals(Cell.ALIVE, grid.getCell(2, 1));
            assertEquals(Cell.ALIVE, grid.getCell(2, 2));
        }
    }

    @Test
    void testGliderPattern() {
        Grid grid = new Grid(10, 10);
        Pattern.GLIDER.applyTo(grid);

        // Glider initial shape
        assertEquals(Cell.ALIVE, grid.getCell(0, 1));
        assertEquals(Cell.ALIVE, grid.getCell(1, 2));
        assertEquals(Cell.ALIVE, grid.getCell(2, 0));
        assertEquals(Cell.ALIVE, grid.getCell(2, 1));
        assertEquals(Cell.ALIVE, grid.getCell(2, 2));
    }

    @Test
    void testGliderMoves() {
        GameOfLife game = new GameOfLife(10, 10);
        Pattern.GLIDER.applyTo(game.getGrid());

        // Count alive cells - glider always has exactly 5
        int initialCount = countAliveCells(game.getGrid());
        assertEquals(5, initialCount);

        // After 4 generations, glider should have same shape but moved
        for (int i = 0; i < 4; i++) {
            game.nextGeneration();
        }

        // Still 5 cells alive
        assertEquals(5, countAliveCells(game.getGrid()));

        // Shape moved down-right by (1,1)
        Grid grid = game.getGrid();
        assertEquals(Cell.ALIVE, grid.getCell(1, 2));
        assertEquals(Cell.ALIVE, grid.getCell(2, 3));
        assertEquals(Cell.ALIVE, grid.getCell(3, 1));
        assertEquals(Cell.ALIVE, grid.getCell(3, 2));
        assertEquals(Cell.ALIVE, grid.getCell(3, 3));
    }

    @Test
    void testPatternFromString() {
        assertEquals(Pattern.BLINKER, Pattern.fromString("blinker"));
        assertEquals(Pattern.BLINKER, Pattern.fromString("BLINKER"));
        assertEquals(Pattern.BLOCK, Pattern.fromString("block"));
        assertEquals(Pattern.GLIDER, Pattern.fromString("Glider"));
    }

    @Test
    void testPatternFromStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Pattern.fromString("invalid"));
    }

    private int countAliveCells(Grid grid) {
        int count = 0;
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                if (grid.getCell(row, col).isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }
}
