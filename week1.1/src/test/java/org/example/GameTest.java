package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void tc09_tc10_tc11_tc11a_human_then_computer_flow_and_board_updates() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "5\n9\n8\n");

        assertNull(result.throwable());
        String output = result.output();
        assertTrue(output.contains("| 2 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
        assertInOrder(output, "Player#1's turn", "Player#2's turn", "Player#1's turn");
        assertTrue(output.contains("Player#2 won!"));
    }

    @Test
    void tc11b_and_atc11_computer_start_handoff_is_correct() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"2"}, "5\n9\n8\n");

        assertNull(result.throwable());
        String output = result.output();
        assertInOrder(output, "Player#2's turn", "Player#1's turn", "Player#2's turn");
        assertTrue(output.contains("| 2 | 0 | 0 |"));
    }

    @Test
    void tc12_human_win_path_terminates_without_extra_prompt() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "1\n4\n7\n9\n");

        assertNull(result.throwable());
        String output = result.output();
        assertTrue(output.contains("Player#1 won!"));
        assertFalse(output.trim().endsWith("turn"));
    }

    @Test
    void tc13_computer_win_path_terminates_without_extra_prompt() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "5\n9\n8\n");

        assertNull(result.throwable());
        assertTrue(result.output().contains("Player#2 won!"));
        assertFalse(result.output().trim().endsWith("turn"));
    }

    @Test
    void tc14_draw_after_human_last_move() {
        Board board = new Board();
        preload(board, 1, 1, 2, 2, 3, 1, 4, 1, 5, 2, 6, 2, 7, 2, 8, 1);
        Player p1 = new ScriptedPlayer(1, 9);
        Player p2 = new ScriptedPlayer(2, 1);
        Game game = new Game(board, p1, p2);

        TestConsoleSupport.RunResult result = TestConsoleSupport.runGame(game);

        assertNull(result.throwable());
        assertTrue(result.output().contains("It is a draw!"));
    }

    @Test
    void tc15_draw_after_computer_last_move() {
        Board board = new Board();
        preload(board, 1, 1, 2, 2, 3, 1, 4, 1, 5, 2, 6, 2, 7, 2, 8, 1);
        Player p1 = new ScriptedPlayer(1, 1);
        Player p2 = new ScriptedPlayer(2, 9);
        Game game = new Game(board, p2, p1);

        TestConsoleSupport.RunResult result = TestConsoleSupport.runGame(game);

        assertNull(result.throwable());
        assertTrue(result.output().contains("It is a draw!"));
    }

    @Test
    void tc16_winner_takes_precedence_over_draw_on_last_cell() {
        Board board = new Board();
        preload(board, 1, 1, 2, 2, 3, 2, 4, 2, 5, 1, 6, 1, 7, 1, 8, 2);
        Player p1 = new ScriptedPlayer(1, 9);
        Player p2 = new ScriptedPlayer(2, 1);
        Game game = new Game(board, p1, p2);

        TestConsoleSupport.RunResult result = TestConsoleSupport.runGame(game);

        assertNull(result.throwable());
        assertTrue(result.output().contains("Player#1 won!"));
        assertFalse(result.output().contains("It is a draw!"));
    }

    @Test
    void tc17_board_state_stays_cumulative_full_game() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "1\n4\n7\n");

        String output = result.output();
        assertTrue(output.contains("| 1 | 0 | 0 |"));
        assertTrue(output.contains("| 1 | 2 | 2 |\n| 1 | 0 | 0 |"));
    }

    @Test
    void tc18_and_atc09_no_additional_prompt_after_terminal_even_with_queued_input() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "1\n4\n7\n5\n6\n");

        assertTrue(result.output().contains("Player#1 won!"));
        String afterWin = result.output().substring(result.output().indexOf("Player#1 won!"));
        assertFalse(afterWin.contains("turn"));
    }

    @Test
    void atc07_rapid_burst_inputs_apply_one_move_per_turn() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "5\n6\n7\n");

        String output = result.output();
        assertTrue(output.contains("| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |"));
        assertTrue(output.contains("| 2 | 0 | 0 |\n| 0 | 1 | 1 |\n| 0 | 0 | 0 |"));
    }

    @Test
    void atc08_fast_invalid_then_valid_applies_single_valid_move() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "x\n5\n9\n8\n");

        String output = result.output();
        assertTrue(output.contains("Please, input a valid number [1-9]"));
        assertEqualsOnce(output, "| 0 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 0 |");
    }

    @Test
    void atc10_valid_high_to_low_pattern_is_handled() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "9\n8\n7\n6\n5\n4\n3\n2\n1\n");

        assertNull(result.throwable());
        String output = result.output();
        assertTrue(output.contains("won!") || output.contains("It is a draw!"));
    }

    @Test
    void atc12_valid_after_multiple_retries_in_same_turn() {
        TestConsoleSupport.RunResult result = TestConsoleSupport.runMain(new String[] {"1"}, "a\n0\n5\n1\n9\n8\n");

        String output = result.output();
        assertTrue(output.contains("Please, input a valid number [1-9]"));
        assertTrue(output.contains("The cell is occupied!"));
        assertTrue(output.contains("| 2 | 0 | 0 |\n| 0 | 1 | 0 |\n| 0 | 0 | 1 |"));
    }

    private static void preload(Board board, int... placements) {
        for (int i = 0; i < placements.length; i += 2) {
            board.placeMove(placements[i], placements[i + 1]);
        }
    }

    private static void assertInOrder(String output, String a, String b, String c) {
        int aPos = output.indexOf(a);
        int bPos = output.indexOf(b, aPos + 1);
        int cPos = output.indexOf(c, bPos + 1);

        assertTrue(aPos >= 0);
        assertTrue(bPos > aPos);
        assertTrue(cPos > bPos);
    }

    private static void assertEqualsOnce(String text, String token) {
        int first = text.indexOf(token);
        int second = text.indexOf(token, first + 1);
        assertTrue(first >= 0);
        assertTrue(second < 0);
    }

    private static final class ScriptedPlayer extends Player {
        private final Queue<Integer> moves;

        private ScriptedPlayer(int playerNumber, int... sequence) {
            super(playerNumber);
            this.moves = new ArrayDeque<>();
            for (int v : sequence) {
                this.moves.offer(v);
            }
        }

        @Override
        public int chooseCell(Board board) {
            Integer next = moves.poll();
            if (next == null) {
                throw new IllegalStateException("No scripted moves left for player " + getPlayerNumber());
            }
            return next;
        }
    }
}
