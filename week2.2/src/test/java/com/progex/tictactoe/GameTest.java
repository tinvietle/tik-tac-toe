package com.progex.tictactoe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void playStopsWhenFirstPlayerWins() {
        Board board = new Board();
        Player p1 = new SequencePlayer("P1", 1, 1, 2, 3);
        Player p2 = new SequencePlayer("P2", 2, 4, 5, 6);
        Game game = new Game(board, p1, p2);

        String output = captureOutput(game::play);

        assertTrue(board.hasWinner(1));
        assertTrue(output.contains("P1 wins."));
    }

    @Test
    void playEndsInDrawWhenBoardIsFullWithoutWinner() {
        Board board = new Board();
        Player p1 = new SequencePlayer("P1", 1, 1, 3, 4, 8, 9);
        Player p2 = new SequencePlayer("P2", 2, 2, 5, 6, 7);
        Game game = new Game(board, p1, p2);

        String output = captureOutput(game::play);

        assertFalse(board.hasWinner(1));
        assertFalse(board.hasWinner(2));
        assertFalse(board.hasFreeCell());
        assertTrue(output.contains("Game finished (draw). No free cells left."));
    }

    private static String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            action.run();
            return output.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(originalOut);
        }
    }

    private static final class SequencePlayer implements Player {
        private final String name;
        private final int mark;
        private final Queue<Integer> moves;

        private SequencePlayer(String name, int mark, Integer... moves) {
            this.name = name;
            this.mark = mark;
            this.moves = new ArrayDeque<>(Arrays.asList(moves));
        }

        @Override
        public int getMark() {
            return mark;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int chooseMove(Board board) {
            Integer next = moves.poll();
            if (next == null) {
                throw new IllegalStateException("No moves left for test player " + name);
            }
            return next;
        }
    }
}
