package com.progex.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void playStopsWhenFirstPlayerWinsAndNotifiesView() {
        Board board = new Board();
        Player p1 = new SequencePlayer("P1", 1, 1, 2, 3);
        Player p2 = new SequencePlayer("P2", 2, 4, 5);
        RecordingGameView view = new RecordingGameView();
        Game game = new Game(board, p1, p2, view);

        game.play();

        assertTrue(board.hasWinner(1));
        assertFalse(view.drawCalled);
        assertNotNull(view.winner);
        assertEquals("P1", view.winner.getName());
        assertEquals(1, view.gameStartCalls);
        assertEquals(5, view.moveEvents.size());
        assertIterableEquals(
            Arrays.asList("P1@1", "P2@4", "P1@2", "P2@5", "P1@3"),
            view.moveEvents
        );
    }

    @Test
    void playEndsInDrawWhenBoardIsFullWithoutWinnerAndNotifiesView() {
        Board board = new Board();
        Player p1 = new SequencePlayer("P1", 1, 1, 3, 4, 8, 9);
        Player p2 = new SequencePlayer("P2", 2, 2, 5, 6, 7);
        RecordingGameView view = new RecordingGameView();
        Game game = new Game(board, p1, p2, view);

        game.play();

        assertFalse(board.hasWinner(1));
        assertFalse(board.hasWinner(2));
        assertFalse(board.hasFreeCell());
        assertTrue(view.drawCalled);
        assertNull(view.winner);
        assertEquals(1, view.gameStartCalls);
        assertEquals(9, view.moveEvents.size());
    }

    @Test
    void playDoesNotTriggerDrawWhenWinnerExists() {
        Board board = new Board();
        Player p1 = new SequencePlayer("P1", 1, 1, 2, 3);
        Player p2 = new SequencePlayer("P2", 2, 4, 5);
        RecordingGameView view = new RecordingGameView();
        Game game = new Game(board, p1, p2, view);

        game.play();

        assertNotNull(view.winner);
        assertFalse(view.drawCalled);
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

    private static final class RecordingGameView implements GameView {
        private int gameStartCalls;
        private final List<String> moveEvents = new ArrayList<>();
        private Player winner;
        private boolean drawCalled;

        @Override
        public void onGameStart(Board board) {
            gameStartCalls++;
        }

        @Override
        public void onMoveMade(Board board, Player player, int position) {
            moveEvents.add(player.getName() + "@" + position);
        }

        @Override
        public void onPlayerWins(Player winner) {
            this.winner = winner;
        }

        @Override
        public void onDraw() {
            this.drawCalled = true;
        }

        @Override
        public int requestMove(Board board, Player player) {
            throw new UnsupportedOperationException("requestMove is not used in this test setup");
        }
    }
}
