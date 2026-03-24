package com.tictactoe;

public class ComputerPlayer implements Player {
    private final int marker;

    public ComputerPlayer(int marker) {
        this.marker = marker;
    }

    @Override
    public int getMarker() {
        return marker;
    }

    @Override
    public int chooseMove(Board board) {
        for (int position = 1; position <= 9; position++) {
            if (board.isPositionFree(position)) {
                return position;
            }
        }
        return -1;
    }
}
