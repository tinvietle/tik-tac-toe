package com.progex.tictactoe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void newBoardHasNoWinnerAndHasFreeCells() {
        Board board = new Board();

        assertTrue(board.hasFreeCell());
        assertFalse(board.hasWinner(1));
        assertFalse(board.hasWinner(2));
    }

    @Test
    void validPositionRangeIsOneToNine() {
        Board board = new Board();

        assertFalse(board.isValidPosition(0));
        assertTrue(board.isValidPosition(1));
        assertTrue(board.isValidPosition(9));
        assertFalse(board.isValidPosition(10));
    }

    @Test
    void placeMarkRejectsInvalidOrUsedPositions() {
        Board board = new Board();

        assertFalse(board.placeMark(0, 1));
        assertTrue(board.placeMark(1, 1));
        assertFalse(board.placeMark(1, 2));
    }

    @Test
    void hasWinnerDetectsRowColumnAndDiagonal() {
        Board rowWin = new Board();
        rowWin.placeMark(1, 1);
        rowWin.placeMark(2, 1);
        rowWin.placeMark(3, 1);
        assertTrue(rowWin.hasWinner(1));

        Board columnWin = new Board();
        columnWin.placeMark(1, 2);
        columnWin.placeMark(4, 2);
        columnWin.placeMark(7, 2);
        assertTrue(columnWin.hasWinner(2));

        Board diagonalWin = new Board();
        diagonalWin.placeMark(1, 1);
        diagonalWin.placeMark(5, 1);
        diagonalWin.placeMark(9, 1);
        assertTrue(diagonalWin.hasWinner(1));
    }

    @Test
    void hasFreeCellBecomesFalseWhenBoardIsFull() {
        Board board = new Board();
        for (int i = 1; i <= 9; i++) {
            board.placeMark(i, (i % 2) + 1);
        }

        assertFalse(board.hasFreeCell());
    }
}
