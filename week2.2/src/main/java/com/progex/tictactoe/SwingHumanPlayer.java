package com.progex.tictactoe;

public class SwingHumanPlayer implements Player {
    private final int mark;
    private final GameView view;

    public SwingHumanPlayer(int mark, GameView view) {
        this.mark = mark;
        this.view = view;
    }

    @Override
    public int getMark() {
        return mark;
    }

    @Override
    public String getName() {
        return "User";
    }

    @Override
    public int chooseMove(Board board) {
        return view.requestMove(board, this);
    }
}
