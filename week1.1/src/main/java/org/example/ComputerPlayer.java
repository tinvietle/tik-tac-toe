package org.example;

public class ComputerPlayer extends Player {
    public ComputerPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public int chooseCell(Board board) {
        return board.firstFreeCell();
    }
}
