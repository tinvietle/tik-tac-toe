package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ComputerPlayerTest {

    @Test
    void tc10_tc11_and_atc11_computer_chooses_first_free_cell() {
        Board board = new Board();
        board.placeMove(1, 1);
        board.placeMove(2, 2);
        board.placeMove(3, 1);

        ComputerPlayer computer = new ComputerPlayer(2);

        assertEquals(4, computer.chooseCell(board));
    }
}
