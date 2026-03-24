package com.tictactoe;

public interface Player {
    int getMarker();
    int chooseMove(Board board);
}
