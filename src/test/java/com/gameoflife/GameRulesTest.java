package com.gameoflife;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class GameRulesTest {
    private GameRules rules;

    @BeforeEach
    void setUp() {
        rules = new GameRules();
    }

    @Test
    void testUnderpopulation_ZeroNeighbors() {
        // Rule 1: Living cell with < 2 neighbors dies
        assertEquals(Cell.DEAD, rules.getNextState(Cell.ALIVE, 0));
    }

    @Test
    void testUnderpopulation_OneNeighbor() {
        // Rule 1: Living cell with < 2 neighbors dies
        assertEquals(Cell.DEAD, rules.getNextState(Cell.ALIVE, 1));
    }

    @Test
    void testSurvival_TwoNeighbors() {
        // Rule 2: Living cell with 2 or 3 neighbors survives
        assertEquals(Cell.ALIVE, rules.getNextState(Cell.ALIVE, 2));
    }

    @Test
    void testSurvival_ThreeNeighbors() {
        // Rule 2: Living cell with 2 or 3 neighbors survives
        assertEquals(Cell.ALIVE, rules.getNextState(Cell.ALIVE, 3));
    }

    @Test
    void testOverpopulation_FourNeighbors() {
        // Rule 3: Living cell with > 3 neighbors dies
        assertEquals(Cell.DEAD, rules.getNextState(Cell.ALIVE, 4));
    }

    @Test
    void testOverpopulation_EightNeighbors() {
        // Rule 3: Living cell with > 3 neighbors dies
        assertEquals(Cell.DEAD, rules.getNextState(Cell.ALIVE, 8));
    }

    @Test
    void testReproduction_ExactlyThreeNeighbors() {
        // Rule 4: Dead cell with exactly 3 neighbors becomes alive
        assertEquals(Cell.ALIVE, rules.getNextState(Cell.DEAD, 3));
    }

    @Test
    void testDeadStaysDead_TwoNeighbors() {
        // Dead cell with != 3 neighbors stays dead
        assertEquals(Cell.DEAD, rules.getNextState(Cell.DEAD, 2));
    }

    @Test
    void testDeadStaysDead_FourNeighbors() {
        // Dead cell with != 3 neighbors stays dead
        assertEquals(Cell.DEAD, rules.getNextState(Cell.DEAD, 4));
    }

    @ParameterizedTest
    @CsvSource({
        "ALIVE, 0, DEAD",
        "ALIVE, 1, DEAD",
        "ALIVE, 2, ALIVE",
        "ALIVE, 3, ALIVE",
        "ALIVE, 4, DEAD",
        "ALIVE, 5, DEAD",
        "ALIVE, 6, DEAD",
        "ALIVE, 7, DEAD",
        "ALIVE, 8, DEAD",
        "DEAD, 0, DEAD",
        "DEAD, 1, DEAD",
        "DEAD, 2, DEAD",
        "DEAD, 3, ALIVE",
        "DEAD, 4, DEAD",
        "DEAD, 5, DEAD",
        "DEAD, 6, DEAD",
        "DEAD, 7, DEAD",
        "DEAD, 8, DEAD"
    })
    void testAllCombinations(Cell currentState, int neighbors, Cell expectedState) {
        assertEquals(expectedState, rules.getNextState(currentState, neighbors));
    }
}
