package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimingAndRobustnessTests {

    @Test
    @DisplayName("ATC-07: rapid consecutive inputs apply one move per turn")
    void atc07_rapidConsecutiveInputs() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "5\n6\n7\n9\n");

        assertTrue(result.stdout().contains("| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
        assertTrue(result.stdout().contains("| 2 | 2 | 2 |") || result.stdout().contains("Player#1 won!"));
    }

    @Test
    @DisplayName("ATC-08: fast invalid-then-valid sequence is handled correctly")
    void atc08_fastInvalidThenValid() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "x\n5\n9\n8\n4\n");

        assertEquals(1, GameTestHarness.countOccurrences(result.stdout(), "Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
    }

    @Test
    @DisplayName("ATC-11: computer starts and game continues normally")
    void atc11_startWithComputerAndContinue() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"2"}, "5\n8\n9\n");

        assertTrue(result.stdout().contains("Player#2's turn"));
        assertTrue(result.stdout().contains("| 2 | 0 | 0 |"));
        assertTrue(result.stdout().contains("| 2 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
    }

    @Test
    @DisplayName("ATC-12 duplicate safety: retried corrections do not duplicate moves")
    void atc12_noDuplicatedMovesAfterRetries() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "a\n0\n5\n9\n8\n4\n");

        String firstHumanBoard = "| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |";
        assertEquals(1, GameTestHarness.countOccurrences(result.stdout(), firstHumanBoard));
    }

    @Test
    @DisplayName("ATC-15 partial: long invalid streak does not crash")
    void atc15_noCrashDuringInvalidStreak() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            input.append("invalid\n");
        }
        input.append("1\n5\n9\n");

        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input.toString());

        assertFalse(result.stdout().isEmpty());
        assertTrue(result.stdout().contains("Please, input a valid number [1-9]"));
        assertEquals(null, result.throwable());
    }

    @Test
    @Disabled("TC-20 is marked non-automatable in the test case document")
    @DisplayName("TC-20: EOF/interrupted input handling (manual)")
    void tc20_eofInterruptedInputManual() {
    }

    @Test
    @Disabled("ATC-16 is marked non-automatable in the test case document")
    @DisplayName("ATC-16: interrupted input stream recovery (manual)")
    void atc16_interruptedInputStreamManual() {
    }
}
