package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StartupAndCliTests {

    @Test
    @DisplayName("TC-01: startup without argument")
    void tc01_startupWithoutArgument() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {}, "");

        assertEquals("Please, input a valid option [1-2]\n", result.stdout());
        assertEquals("", result.stderr());
        assertEquals(null, result.throwable());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "3", "-1", "abc"})
    @DisplayName("TC-02: startup with invalid argument values")
    void tc02_startupWithInvalidArgument(String arg) {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {arg}, "");

        assertEquals("Please, input a valid option [1-2]\n", result.stdout());
        assertEquals("", result.stderr());
        assertEquals(null, result.throwable());
    }

    @Test
    @DisplayName("TC-03: startup with extra arguments is consistently rejected")
    void tc03_startupWithExtraArguments() {
        GameTestHarness.RunResult resultOne = GameTestHarness.run(new String[] {"1", "extra"}, "");
        GameTestHarness.RunResult resultTwo = GameTestHarness.run(new String[] {"2", "extra"}, "");

        assertEquals("Please, input a valid option [1-2]\n", resultOne.stdout());
        assertEquals("Please, input a valid option [1-2]\n", resultTwo.stdout());
    }

    @Test
    @DisplayName("TC-04 + ATC-03: startup output order and boundary options 1/2")
    void tc04_startupOrderAndBoundaryOptions() {
        GameTestHarness.RunResult startOne = GameTestHarness.run(new String[] {"1"}, "1\n5\n9\n");
        GameTestHarness.assertContainsInOrder(startOne.stdout(),
            "Hello!\n",
            "| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#1's turn\n");

        GameTestHarness.RunResult startTwo = GameTestHarness.run(new String[] {"2"}, "5\n8\n9\n");
        GameTestHarness.assertContainsInOrder(startTwo.stdout(),
            "Hello!\n",
            "| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#2's turn\n");

        assertFalse(startOne.stdout().contains("Please, input a valid option [1-2]"));
        assertFalse(startTwo.stdout().contains("Please, input a valid option [1-2]"));
    }

    @Test
    @DisplayName("TC-23 (part): startup turn prompt apostrophe/message consistency")
    void tc23_startupTurnPromptConsistency() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "1\n5\n9\n");
        assertTrue(result.stdout().contains("Player#1's turn"));
        assertFalse(result.stdout().contains("Player#1’s turn"));
    }
}
