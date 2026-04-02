package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void tc01_startup_without_argument_rejected() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {}, "");

        assertNull(result.throwable());
        assertTrue(result.output().contains("Please, input a valid option [1-2]"));
        assertFalse(result.output().contains("Hello!"));
    }

    @Test
    void tc02_startup_with_invalid_arguments_rejected() {
        assertRejectedArg("0");
        assertRejectedArg("3");
        assertRejectedArg("-1");
        assertRejectedArg("abc");
    }

    @Test
    void tc03_extra_arguments_are_rejected_consistently() {
        TestConsoleSupport.RunResult r1 = TestConsoleSupport.runMain(new String[] {"1", "extra"}, "");
        TestConsoleSupport.RunResult r2 = TestConsoleSupport.runMain(new String[] {"2", "extra"}, "");

        assertTrue(r1.output().contains("Please, input a valid option [1-2]"));
        assertTrue(r2.output().contains("Please, input a valid option [1-2]"));
    }

    @Test
    void tc04_and_atc03_startup_order_and_turn_prompt_for_player1() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "1\n4\n7\n");

        assertNull(result.throwable());
        assertInOrder(result.output(), "Hello!", "| 0 | 0 | 0 |", "Player#1's turn");
    }

    @Test
    void tc04_and_atc03_startup_order_and_turn_prompt_for_player2() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"2"}, "5\n9\n8\n");

        assertNull(result.throwable());
        assertInOrder(result.output(), "Hello!", "| 0 | 0 | 0 |", "Player#2's turn");
    }

    @Test
    void tc23_messages_use_straight_apostrophe_consistently() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "x\n1\n4\n7\n");

        assertTrue(result.output().contains("Player#1's turn"));
        assertFalse(result.output().contains("Player#1’s turn"));
        assertTrue(result.output().contains("Please, input a valid number [1-9]"));
        assertTrue(result.output().contains("Player#1 won!"));
    }

    private static void assertRejectedArg(String arg) {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {arg}, "");
        assertNull(result.throwable());
        assertTrue(result.output().contains("Please, input a valid option [1-2]"));
        assertFalse(result.output().contains("Hello!"));
    }

    private static void assertInOrder(String output, String first, String second, String third) {
        int firstPos = output.indexOf(first);
        int secondPos = output.indexOf(second);
        int thirdPos = output.indexOf(third);

        assertTrue(firstPos >= 0);
        assertTrue(secondPos > firstPos);
        assertTrue(thirdPos > secondPos);
    }
}
