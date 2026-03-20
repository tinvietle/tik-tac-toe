package com.progex.tictactoe;

public interface Player {
    int getMark();

    String getName();

    int chooseMove(Board board);
}
