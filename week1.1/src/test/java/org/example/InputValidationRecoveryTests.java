package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidationRecoveryTests {

    @Test
    @DisplayName("TC-06: non-integer human input is rejected and turn remains Player#1")
    void tc06_nonIntegerInput() {
        String input = "x\n1.5\nabc\n1\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertEquals(3, GameTestHarness.countOccurrences(result.stdout(), "Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("Player#1's turn\nPlayer#1's turn")
            || result.stdout().contains("Please, input a valid number [1-9]\nPlayer#1's turn"));
    }

    @Test
    @DisplayName("TC-07: out-of-range integers are rejected")
    void tc07_outOfRangeIntegerInput() {
        String input = "0\n10\n-5\n1\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertEquals(3, GameTestHarness.countOccurrences(result.stdout(), "Please, input a valid number [1-9]"));
        assertFalse(result.stdout().contains("The cell is occupied!"));
    }

    @Test
    @DisplayName("TC-08: occupied cell is rejected and player retries")
    void tc08_occupiedCellInput() {
        String input = "1\n1\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertTrue(result.stdout().contains("The cell is occupied!"));
        assertTrue(result.stdout().contains("The cell is occupied!\nPlayer#1's turn"));
    }

    @Test
    @DisplayName("TC-19: leading/trailing spaces accepted, blank line rejected")
    void tc19_whitespaceHandling() {
        GameTestHarness.RunResult withLeadingSpace = GameTestHarness.run(new String[] {"1"}, " 5\n9\n8\n4\n");
        assertFalse(withLeadingSpace.stdout().contains("Please, input a valid number [1-9]"));

        GameTestHarness.RunResult withBlank = GameTestHarness.run(new String[] {"1"}, "\n1\n5\n9\n");
        assertTrue(withBlank.stdout().contains("Please, input a valid number [1-9]"));
    }

    @Test
    @DisplayName("ATC-04: extremely large integer tokens are rejected safely")
    void atc04_extremelyLargeIntegerInput() {
        String input = "999999999\n2147483648\n1\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertEquals(2, GameTestHarness.countOccurrences(result.stdout(), "Please, input a valid number [1-9]"));
        assertEquals("", result.stderr());
        assertEquals(null, result.throwable());
    }

    @Test
    @DisplayName("ATC-05: special characters and mixed tokens are rejected")
    void atc05_specialCharactersAndMixedTokens() {
        String input = "@\n3a\na3\n5,\n1\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertEquals(4, GameTestHarness.countOccurrences(result.stdout(), "Please, input a valid number [1-9]"));
    }

    @Test
    @DisplayName("ATC-06: multi-token single line is rejected and recovery works")
    void atc06_multiTokenSingleLineInput() {
        String input = "5 6\n5\n9\n8\n4\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertTrue(result.stdout().contains("Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
    }

    @Test
    @DisplayName("ATC-12: valid input after multiple retries")
    void atc12_validAfterMultipleRetries() {
        String input = "a\n0\n1\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertTrue(result.stdout().contains("Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("The cell is occupied!") || result.stdout().contains("Player#1 won!"));
    }

    @Test
    @DisplayName("ATC-13: recovery after non-integer input")
    void atc13_recoveryAfterNonInteger() {
        String input = "hello\n4\n5\n9\n8\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertTrue(result.stdout().contains("Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("| 0 | 0 | 0 |\n| 1 | 0 | 0 |\n| 0 | 0 | 0 |"));
    }

    @Test
    @DisplayName("ATC-14: recovery after occupied-cell rejection")
    void atc14_recoveryAfterOccupiedCell() {
        String input = "1\n1\n4\n5\n9\n";
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input);

        assertTrue(result.stdout().contains("The cell is occupied!"));
        assertTrue(result.stdout().contains("| 1 | 2 | 0 |\n| 1 | 0 | 0 |\n| 0 | 0 | 0 |"));
    }

    @Test
    @DisplayName("ATC-15: program remains responsive after long invalid streak")
    void atc15_longInvalidInputStreak() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            input.append("x\n");
            input.append("0\n");
        }
        input.append("1\n5\n9\n");

        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, input.toString());
        assertEquals(20, GameTestHarness.countOccurrences(result.stdout(), "Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("Player#1 won!"));
    }
}
