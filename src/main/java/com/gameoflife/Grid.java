package com.gameoflife;

import java.util.Random;

/**
 * Represents the grid of cells in the Game of Life.
 * Implements toroidal wrapping (edges connect to opposite edges).
 */
public class Grid {
    private final int width;
    private final int height;
    private Cell[][] cells;

    public Grid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        initializeAllDead();
    }

    private void initializeAllDead() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = Cell.DEAD;
            }
        }
    }

    /**
     * Randomly initializes the grid with alive/dead cells.
     * @param aliveProbability probability (0.0 to 1.0) that a cell is alive
     */
    public void randomize(double aliveProbability) {
        Random random = new Random();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = random.nextDouble() < aliveProbability ? Cell.ALIVE : Cell.DEAD;
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[wrapRow(row)][wrapCol(col)];
    }

    public void setCell(int row, int col, Cell cell) {
        cells[wrapRow(row)][wrapCol(col)] = cell;
    }

    /**
     * Counts the number of alive neighbors for a given cell.
     * Uses toroidal wrapping for edge cells.
     */
    public int countAliveNeighbors(int row, int col) {
        int count = 0;
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) {
                    continue; // Skip the cell itself
                }
                if (getCell(row + dRow, col + dCol).isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Wraps row index for toroidal grid.
     */
    private int wrapRow(int row) {
        return Math.floorMod(row, height);
    }

    /**
     * Wraps column index for toroidal grid.
     */
    private int wrapCol(int col) {
        return Math.floorMod(col, width);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Updates this grid's cells from another grid.
     */
    public void copyFrom(Grid other) {
        if (other.width != this.width || other.height != this.height) {
            throw new IllegalArgumentException("Grid dimensions must match");
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.cells[row][col] = other.cells[row][col];
            }
        }
    }
}
