package com.progex.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class HumanPlayerTest {

    @Test
    void chooseMoveSkipsInvalidInputUntilValidFreePosition() {
        Board board = new Board();
        board.placeMark(1, 2);

        String input = String.join(System.lineSeparator(), "abc", "10", "1", "2")
            + System.lineSeparator();
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        HumanPlayer player = new HumanPlayer(1, scanner);

        int move = player.chooseMove(board);

        assertEquals(2, move);
        scanner.close();
    }
}
