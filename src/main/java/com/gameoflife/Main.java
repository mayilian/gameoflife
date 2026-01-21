package com.gameoflife;

/**
 * Main entry point for the Game of Life application.
 * Provides a command-line interface for running the simulation.
 */
public class Main {
    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 20;
    private static final double DEFAULT_ALIVE_PROBABILITY = 0.3;
    private static final int DEFAULT_DELAY_MS = 200;

    public static void main(String[] args) {
        int width = -1;  // -1 means use default or pattern's recommended size
        int height = -1;
        double aliveProbability = -1;  // -1 means not set
        int delayMs = DEFAULT_DELAY_MS;
        Pattern pattern = null;
        String cellsArg = null;

        // Parse command line arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--width", "-w" -> {
                    if (i + 1 < args.length) width = Integer.parseInt(args[++i]);
                }
                case "--height", "-h" -> {
                    if (i + 1 < args.length) height = Integer.parseInt(args[++i]);
                }
                case "--probability", "-p" -> {
                    if (i + 1 < args.length) aliveProbability = Double.parseDouble(args[++i]);
                }
                case "--delay", "-d" -> {
                    if (i + 1 < args.length) delayMs = Integer.parseInt(args[++i]);
                }
                case "--pattern", "-P" -> {
                    if (i + 1 < args.length) {
                        try {
                            pattern = Pattern.fromString(args[++i]);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Unknown pattern: " + args[i]);
                            System.err.println("Available: blinker, block, glider");
                            return;
                        }
                    }
                }
                case "--cells", "-c" -> {
                    if (i + 1 < args.length) cellsArg = args[++i];
                }
                case "--help" -> {
                    printHelp();
                    return;
                }
            }
        }

        // Determine initialization mode and grid size
        if (pattern != null) {
            // Pattern mode: use pattern's recommended size if not specified
            if (width == -1) width = pattern.getRecommendedWidth();
            if (height == -1) height = pattern.getRecommendedHeight();
            runWithPattern(width, height, pattern, delayMs);
        } else if (cellsArg != null) {
            // Explicit cells mode
            if (width == -1) width = DEFAULT_WIDTH;
            if (height == -1) height = DEFAULT_HEIGHT;
            runWithCells(width, height, cellsArg, delayMs);
        } else {
            // Random mode
            if (width == -1) width = DEFAULT_WIDTH;
            if (height == -1) height = DEFAULT_HEIGHT;
            if (aliveProbability == -1) aliveProbability = DEFAULT_ALIVE_PROBABILITY;
            runWithRandom(width, height, aliveProbability, delayMs);
        }
    }

    private static void runWithPattern(int width, int height, Pattern pattern, int delayMs) {
        GameOfLife game = new GameOfLife(width, height);
        pattern.applyTo(game.getGrid());
        runSimulation(game, "Pattern: " + pattern.name(), delayMs);
    }

    private static void runWithCells(int width, int height, String cellsArg, int delayMs) {
        GameOfLife game = new GameOfLife(width, height);
        Grid grid = game.getGrid();

        // Parse cells: "row,col;row,col;..." e.g., "1,2;1,3;1,4"
        for (String cellStr : cellsArg.split(";")) {
            String[] parts = cellStr.trim().split(",");
            if (parts.length == 2) {
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());
                grid.setCell(row, col, Cell.ALIVE);
            }
        }
        runSimulation(game, "Custom cells", delayMs);
    }

    private static void runWithRandom(int width, int height, double aliveProbability, int delayMs) {
        GameOfLife game = new GameOfLife(width, height);
        game.randomize(aliveProbability);
        runSimulation(game, "Random (p=" + aliveProbability + ")", delayMs);
    }

    private static void runSimulation(GameOfLife game, String mode, int delayMs) {
        ConsoleRenderer renderer = new ConsoleRenderer();

        System.out.println("Conway's Game of Life");
        System.out.println("Grid: " + game.getWidth() + "x" + game.getHeight());
        System.out.println("Mode: " + mode);
        System.out.println("Press Ctrl+C to stop\n");

        try {
            while (true) {
                renderer.clearScreen();
                renderer.display(game);
                game.nextGeneration();
                Thread.sleep(delayMs);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\nSimulation stopped.");
        }
    }

    private static void printHelp() {
        System.out.println("Conway's Game of Life");
        System.out.println();
        System.out.println("Usage: java -jar gameoflife.jar [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -w, --width <n>        Grid width (default: " + DEFAULT_WIDTH + ")");
        System.out.println("  -h, --height <n>       Grid height (default: " + DEFAULT_HEIGHT + ")");
        System.out.println("  -d, --delay <ms>       Delay between generations (default: " + DEFAULT_DELAY_MS + ")");
        System.out.println();
        System.out.println("Initialization (choose one):");
        System.out.println("  -p, --probability <n>  Random with alive probability 0.0-1.0 (default: " + DEFAULT_ALIVE_PROBABILITY + ")");
        System.out.println("  -P, --pattern <name>   Use preset pattern: blinker, block, glider");
        System.out.println("  -c, --cells <coords>   Set specific cells: \"row,col;row,col;...\"");
        System.out.println();
        System.out.println("Patterns for verification:");
        System.out.println("  blinker  - Oscillates vertically/horizontally (period 2)");
        System.out.println("  block    - Static 2x2 square (never changes)");
        System.out.println("  glider   - Moves diagonally across grid");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  --pattern blinker              # Visual verification");
        System.out.println("  --pattern glider -d 300        # Slower glider");
        System.out.println("  --cells \"1,1;1,2;1,3\" -w 5 -h 5  # Custom blinker");
        System.out.println("  -p 0.4 -w 30 -h 15             # Dense random");
        System.out.println();
        System.out.println("  --help                 Show this help message");
    }
}
