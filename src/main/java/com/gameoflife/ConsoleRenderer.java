package com.gameoflife;

/**
 * Renders the Game of Life grid to the console with colors and grid lines.
 */
public class ConsoleRenderer {
    // ANSI color codes
    private static final String RESET = "\033[0m";
    private static final String GREEN_BG = "\033[42m";       // Green background for alive
    private static final String DARK_GRAY_BG = "\033[100m";  // Dark gray background for dead
    private static final String WHITE = "\033[97m";          // White text for grid lines

    // Box-drawing characters
    private static final String H_LINE = "───";
    private static final String V_LINE = "│";
    private static final String CORNER_TL = "┌";
    private static final String CORNER_TR = "┐";
    private static final String CORNER_BL = "└";
    private static final String CORNER_BR = "┘";
    private static final String T_DOWN = "┬";
    private static final String T_UP = "┴";
    private static final String T_RIGHT = "├";
    private static final String T_LEFT = "┤";
    private static final String CROSS = "┼";

    /**
     * Renders the current state of the game to a string.
     */
    public String render(GameOfLife game) {
        StringBuilder sb = new StringBuilder();
        Grid grid = game.getGrid();

        // Header
        sb.append("Generation: ").append(game.getGeneration()).append("\n");

        // Top border
        sb.append(WHITE).append(CORNER_TL);
        for (int col = 0; col < grid.getWidth(); col++) {
            sb.append(H_LINE);
            sb.append(col < grid.getWidth() - 1 ? T_DOWN : CORNER_TR);
        }
        sb.append(RESET).append("\n");

        // Grid rows
        for (int row = 0; row < grid.getHeight(); row++) {
            // Cell row
            sb.append(WHITE).append(V_LINE).append(RESET);
            for (int col = 0; col < grid.getWidth(); col++) {
                boolean alive = grid.getCell(row, col).isAlive();
                String bg = alive ? GREEN_BG : DARK_GRAY_BG;
                sb.append(bg).append("   ").append(RESET);
                sb.append(WHITE).append(V_LINE).append(RESET);
            }
            sb.append("\n");

            // Horizontal separator (except after last row)
            if (row < grid.getHeight() - 1) {
                sb.append(WHITE).append(T_RIGHT);
                for (int col = 0; col < grid.getWidth(); col++) {
                    sb.append(H_LINE);
                    sb.append(col < grid.getWidth() - 1 ? CROSS : T_LEFT);
                }
                sb.append(RESET).append("\n");
            }
        }

        // Bottom border
        sb.append(WHITE).append(CORNER_BL);
        for (int col = 0; col < grid.getWidth(); col++) {
            sb.append(H_LINE);
            sb.append(col < grid.getWidth() - 1 ? T_UP : CORNER_BR);
        }
        sb.append(RESET);

        return sb.toString();
    }

    /**
     * Clears the console (ANSI escape code).
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the game state to stdout.
     */
    public void display(GameOfLife game) {
        System.out.println(render(game));
    }
}
