package com.progex.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ComputerPlayerTest {

    @Test
    void chooseMoveReturnsFirstFreePosition() {
        Board board = new Board();
        board.placeMark(1, 1);
        board.placeMark(2, 2);

        ComputerPlayer player = new ComputerPlayer(2);

        assertEquals(3, player.chooseMove(board));
    }

    @Test
    void chooseMoveThrowsWhenNoFreePositionExists() {
        Board board = new Board();
        for (int i = 1; i <= 9; i++) {
            board.placeMark(i, (i % 2) + 1);
        }

        ComputerPlayer player = new ComputerPlayer(2);

        assertThrows(IllegalStateException.class, () -> player.chooseMove(board));
    }
}
