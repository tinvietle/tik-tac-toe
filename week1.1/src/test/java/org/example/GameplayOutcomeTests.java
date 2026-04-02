package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameplayOutcomeTests {

    private static final Pattern BOARD_LINE = Pattern.compile("^\\| [012] \\| [012] \\| [012] \\|$", Pattern.MULTILINE);

    @Test
    @DisplayName("TC-05 + ATC-01 + ATC-02: cell mapping boundaries (1,5,9)")
    void tc05_cellMapping() {
        GameTestHarness.RunResult move1 = GameTestHarness.run(new String[] {"1"}, "1\n5\n9\n");
        assertTrue(move1.stdout().contains("| 1 | 0 | 0 |"));

        GameTestHarness.RunResult move5 = GameTestHarness.run(new String[] {"1"}, "5\n9\n8\n4\n");
        assertTrue(move5.stdout().contains("| 0 | 1 | 0 |"));

        GameTestHarness.RunResult move9 = GameTestHarness.run(new String[] {"1"}, "9\n8\n7\n");
        assertTrue(move9.stdout().contains("| 0 | 0 | 1 |"));
    }

    @Test
    @DisplayName("TC-09: human valid move updates board and flow")
    void tc09_humanValidMoveUpdatesBoard() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "5\n9\n8\n4\n");

        GameTestHarness.assertContainsInOrder(result.stdout(),
            "Player#1's turn\n",
            "| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#2's turn\n");
    }

    @Test
    @DisplayName("TC-09A: turn alternates from human to computer and back (start=1)")
    void tc09a_turnAlternation_startWithHuman() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "5\n9\n8\n4\n");

        GameTestHarness.assertContainsInOrder(result.stdout(),
            "Player#1's turn\n",
            "| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#2's turn\n",
            "| 2 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#1's turn\n");
    }

    @Test
    @DisplayName("TC-09B + ATC-11: turn alternates from computer to human and back (start=2)")
    void tc09b_turnAlternation_startWithComputer() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"2"}, "5\n8\n9\n");

        GameTestHarness.assertContainsInOrder(result.stdout(),
            "Player#2's turn\n",
            "| 2 | 0 | 0 |\n| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#1's turn\n",
            "| 2 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |\n",
            "Player#2's turn\n");
    }

    @Test
    @DisplayName("TC-10 + TC-11: computer picks first free cell and writes 2")
    void tc10_tc11_computerStrategyAndMarker() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "5\n9\n8\n4\n");

        assertTrue(result.stdout().contains("| 2 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
        assertTrue(result.stdout().contains("| 2 | 2 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 1 |"));
    }

    @Test
    @DisplayName("TC-12: human win detection and termination")
    void tc12_humanWin() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "1\n5\n9\n");

        assertTrue(result.stdout().contains("Player#1 won!"));
        assertFalse(result.stdout().contains("Player#2 won!"));
        assertFalse(result.stdout().contains("It is a draw!"));
        assertTrue(result.stdout().trim().endsWith("Player#1 won!"));
    }

    @Test
    @DisplayName("TC-13 + ATC-11: computer win detection (including start option 2 path)")
    void tc13_computerWin() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"2"}, "5\n8\n9\n");

        assertTrue(result.stdout().contains("Player#2 won!"));
        assertTrue(result.stdout().contains("Player#2's turn"));
        assertFalse(result.stdout().contains("It is a draw!"));
    }

    @Test
    @DisplayName("TC-14: draw after human move")
    void tc14_drawAfterHumanMove() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "2\n4\n5\n7\n9\n");

        assertTrue(result.stdout().contains("It is a draw!"));
        assertTrue(result.stdout().trim().endsWith("It is a draw!"));
    }

    @Test
    @DisplayName("TC-15: draw after computer move")
    void tc15_drawAfterComputerMove() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"2"}, "2\n4\n7\n9\n");

        assertTrue(result.stdout().contains("It is a draw!"));
        assertTrue(result.stdout().trim().endsWith("It is a draw!"));
    }

    @Test
    @DisplayName("TC-16: winner message has precedence over draw on last cell")
    void tc16_winnerPrecedenceOverDraw() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "1\n3\n5\n8\n9\n");

        assertTrue(result.stdout().contains("Player#1 won!"));
        assertFalse(result.stdout().contains("It is a draw!"));
    }

    @Test
    @DisplayName("TC-17: board state is cumulative through full game")
    void tc17_boardStateConsistency() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "1\n3\n5\n8\n9\n");

        List<String> boardLines = new ArrayList<>();
        Matcher matcher = BOARD_LINE.matcher(result.stdout());
        while (matcher.find()) {
            boardLines.add(matcher.group());
        }

        for (String line : boardLines) {
            assertTrue(line.matches("^\\| [012] \\| [012] \\| [012] \\|$"));
        }
        assertTrue(boardLines.size() >= 9);
    }

    @Test
    @DisplayName("TC-18 + ATC-09: no input accepted after game end")
    void tc18_noInputAfterGameEnd() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "1\n5\n9\n6\n6\n6\n");

        assertTrue(result.stdout().trim().endsWith("Player#1 won!"));
        assertFalse(result.stdout().contains("Player#1 won!\nPlayer#"));
    }

    @Test
    @DisplayName("TC-21: board formatting is stable")
    void tc21_boardFormattingStrictness() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "1\n5\n9\n");

        Matcher matcher = BOARD_LINE.matcher(result.stdout());
        int boardLineCount = 0;
        while (matcher.find()) {
            boardLineCount++;
        }

        assertTrue(boardLineCount >= 6);
        assertEquals(0, boardLineCount % 3);
    }

    @Test
    @DisplayName("TC-23: output strings are consistent across paths")
    void tc23_outputStringConsistency() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "x\n1\n1\n5\n9\n");

        assertTrue(result.stdout().contains("Please, input a valid number [1-9]"));
        assertTrue(result.stdout().contains("The cell is occupied!"));
        assertTrue(result.stdout().contains("Player#1's turn"));
        assertFalse(result.stdout().contains("Player#1’s turn"));
    }

    @Test
    @DisplayName("ATC-10: always selecting highest free cell is processed correctly")
    void atc10_highToLowHumanPattern() {
        GameTestHarness.RunResult result = GameTestHarness.run(new String[] {"1"}, "9\n8\n7\n");

        assertTrue(result.stdout().contains("Player#1 won!"));
        assertTrue(result.stdout().contains("| 1 | 1 | 1 |"));
    }
}
