package com.progex.tictactoe;

public class ComputerPlayer implements Player {
    private final int mark;

    public ComputerPlayer(int mark) {
        this.mark = mark;
    }

    @Override
    public int getMark() {
        return mark;
    }

    @Override
    public String getName() {
        return "Computer";
    }

    @Override
    public int chooseMove(Board board) {
        for (int position = 1; position <= 9; position++) {
            if (board.isFree(position)) {
                return position;
            }
        }
        throw new IllegalStateException("No free position available for computer move.");
    }
}
