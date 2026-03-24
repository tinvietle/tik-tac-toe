package com.tictactoe;

public class HumanPlayer implements Player {
    private final int marker;

    public HumanPlayer(int marker) {
        this.marker = marker;
    }

    @Override
    public int getMarker() {
        return marker;
    }

    @Override
    public int chooseMove(Board board) {
        // Human moves are provided from the Swing event handler.
        return -1;
    }
}
