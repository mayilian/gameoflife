package com.gameoflife;

/**
 * Orchestrates the Game of Life simulation.
 * Manages the grid state and applies rules to compute generations.
 */
public class GameOfLife {
    private Grid grid;
    private final GameRules rules;
    private int generation;

    public GameOfLife(int width, int height) {
        this.grid = new Grid(width, height);
        this.rules = new GameRules();
        this.generation = 0;
    }

    /**
     * Advances the simulation by one generation.
     * All cells are updated simultaneously based on the rules.
     */
    public void nextGeneration() {
        Grid newGrid = new Grid(grid.getWidth(), grid.getHeight());

        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                Cell currentCell = grid.getCell(row, col);
                int aliveNeighbors = grid.countAliveNeighbors(row, col);
                Cell nextState = rules.getNextState(currentCell, aliveNeighbors);
                newGrid.setCell(row, col, nextState);
            }
        }

        grid.copyFrom(newGrid);
        generation++;
    }

    /**
     * Randomly initializes the grid.
     */
    public void randomize(double aliveProbability) {
        grid.randomize(aliveProbability);
        generation = 0;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getGeneration() {
        return generation;
    }

    public int getWidth() {
        return grid.getWidth();
    }

    public int getHeight() {
        return grid.getHeight();
    }
}
