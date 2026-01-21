package com.gameoflife;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(5, 5);
    }

    @Test
    void testGridInitializesAllDead() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                assertEquals(Cell.DEAD, grid.getCell(row, col));
            }
        }
    }

    @Test
    void testSetAndGetCell() {
        grid.setCell(2, 3, Cell.ALIVE);
        assertEquals(Cell.ALIVE, grid.getCell(2, 3));
        assertEquals(Cell.DEAD, grid.getCell(0, 0));
    }

    @Test
    void testToroidalWrappingRight() {
        grid.setCell(2, 4, Cell.ALIVE);
        // Accessing column 5 should wrap to column 0
        assertEquals(Cell.DEAD, grid.getCell(2, 5));
        // Column -1 should wrap to column 4
        grid.setCell(2, -1, Cell.ALIVE);
        assertEquals(Cell.ALIVE, grid.getCell(2, 4));
    }

    @Test
    void testToroidalWrappingBottom() {
        grid.setCell(4, 2, Cell.ALIVE);
        // Accessing row 5 should wrap to row 0
        assertEquals(Cell.DEAD, grid.getCell(5, 2));
        // Row -1 should wrap to row 4
        assertEquals(Cell.ALIVE, grid.getCell(-1, 2));
    }

    @Test
    void testCountAliveNeighborsCenter() {
        // Set up neighbors around cell (2, 2)
        grid.setCell(1, 1, Cell.ALIVE);
        grid.setCell(1, 2, Cell.ALIVE);
        grid.setCell(1, 3, Cell.ALIVE);

        assertEquals(3, grid.countAliveNeighbors(2, 2));
    }

    @Test
    void testCountAliveNeighborsAllEight() {
        // Surround cell (2, 2) with 8 alive neighbors
        grid.setCell(1, 1, Cell.ALIVE);
        grid.setCell(1, 2, Cell.ALIVE);
        grid.setCell(1, 3, Cell.ALIVE);
        grid.setCell(2, 1, Cell.ALIVE);
        grid.setCell(2, 3, Cell.ALIVE);
        grid.setCell(3, 1, Cell.ALIVE);
        grid.setCell(3, 2, Cell.ALIVE);
        grid.setCell(3, 3, Cell.ALIVE);

        assertEquals(8, grid.countAliveNeighbors(2, 2));
    }

    @Test
    void testCountAliveNeighborsCornerWithWrapping() {
        // Test corner cell (0, 0) with toroidal wrapping
        grid.setCell(4, 4, Cell.ALIVE); // wraps to top-left diagonal
        grid.setCell(4, 0, Cell.ALIVE); // wraps to above
        grid.setCell(0, 4, Cell.ALIVE); // wraps to left

        assertEquals(3, grid.countAliveNeighbors(0, 0));
    }

    @Test
    void testCountAliveNeighborsDoesNotCountSelf() {
        grid.setCell(2, 2, Cell.ALIVE);
        assertEquals(0, grid.countAliveNeighbors(2, 2));
    }

    @Test
    void testInvalidGridDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Grid(5, 0));
        assertThrows(IllegalArgumentException.class, () -> new Grid(-1, 5));
    }

    @Test
    void testGetDimensions() {
        Grid g = new Grid(10, 15);
        assertEquals(10, g.getWidth());
        assertEquals(15, g.getHeight());
    }
}
