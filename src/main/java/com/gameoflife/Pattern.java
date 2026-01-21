package com.gameoflife;

/**
 * Predefined patterns for visual verification of Game of Life rules.
 *
 * Each pattern has known behavior:
 * - BLINKER: Period-2 oscillator (vertical ↔ horizontal)
 * - BLOCK: Still life (never changes)
 * - GLIDER: Moves diagonally across the grid
 */
public enum Pattern {
    /**
     * Blinker - simplest oscillator, period 2.
     * Alternates between vertical and horizontal.
     */
    BLINKER(new int[][]{{0, 1}, {1, 1}, {2, 1}}, 5, 5),

    /**
     * Block - simplest still life.
     * 2x2 square that never changes.
     */
    BLOCK(new int[][]{{1, 1}, {1, 2}, {2, 1}, {2, 2}}, 4, 4),

    /**
     * Glider - moves diagonally down-right.
     * Returns to original shape every 4 generations, shifted by (1,1).
     */
    GLIDER(new int[][]{{0, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}, 10, 10);

    private final int[][] cells;
    private final int recommendedWidth;
    private final int recommendedHeight;

    Pattern(int[][] cells, int recommendedWidth, int recommendedHeight) {
        this.cells = cells;
        this.recommendedWidth = recommendedWidth;
        this.recommendedHeight = recommendedHeight;
    }

    /**
     * Returns array of [row, col] coordinates for alive cells.
     */
    public int[][] getCells() {
        return cells;
    }

    public int getRecommendedWidth() {
        return recommendedWidth;
    }

    public int getRecommendedHeight() {
        return recommendedHeight;
    }

    /**
     * Applies this pattern to a grid, centering it.
     */
    public void applyTo(Grid grid) {
        int offsetRow = (grid.getHeight() - recommendedHeight) / 2;
        int offsetCol = (grid.getWidth() - recommendedWidth) / 2;

        for (int[] cell : cells) {
            int row = cell[0] + Math.max(0, offsetRow);
            int col = cell[1] + Math.max(0, offsetCol);
            if (row < grid.getHeight() && col < grid.getWidth()) {
                grid.setCell(row, col, Cell.ALIVE);
            }
        }
    }

    /**
     * Parses pattern name (case-insensitive).
     */
    public static Pattern fromString(String name) {
        return Pattern.valueOf(name.toUpperCase());
    }
}
